package com.example.rustore_app_showcase.data.install

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class InstallManager(private val context: Context) {

    sealed class State {
        object Idle : State()
        object Downloading : State()
        object Installing : State()
        object Installed : State()
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _states = MutableStateFlow<Map<Int, State>>(emptyMap())
    val states = _states.asStateFlow()

    fun getState(appId: Int): State = _states.value[appId] ?: State.Idle

    fun install(appId: Int, apkUrl: String) {
        if (apkUrl.isEmpty()) return
        scope.launch {
            updateState(appId, State.Downloading)
            try {
                val connection = URL(apkUrl).openConnection() as HttpURLConnection
                try {
                    connection.connectTimeout = 15_000
                    connection.readTimeout = 60_000
                    connection.connect()
                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        error("HTTP ${connection.responseCode}")
                    }
                    val bytes = connection.inputStream.readBytes()

                    updateState(appId, State.Installing)

                    val packageInstaller = context.packageManager.packageInstaller
                    val params = PackageInstaller.SessionParams(
                        PackageInstaller.SessionParams.MODE_FULL_INSTALL
                    )
                    val sessionId = packageInstaller.createSession(params)

                    packageInstaller.openSession(sessionId).use { session ->
                        session.openWrite("package", 0, bytes.size.toLong()).use { out ->
                            out.write(bytes)
                            session.fsync(out)
                        }
                        val intent = Intent(context, InstallReceiver::class.java).apply {
                            putExtra(EXTRA_APP_ID, appId)
                        }
                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            sessionId,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                        )
                        session.commit(pendingIntent.intentSender)
                        // Дальнейший результат придёт через InstallReceiver
                    }
                } finally {
                    connection.disconnect()
                }
            } catch (e: Exception) {
                delay(1500)
                updateState(appId, State.Idle)
            }
        }
    }

    fun uninstall(appId: Int) {
        // В реальном приложении здесь PackageManager.deletePackage
        // Для демо: сброс в исходное состояние
        updateState(appId, State.Idle)
    }

    fun onInstallSuccess(appId: Int) {
        updateState(appId, State.Installed)
    }

    fun onInstallFailure(appId: Int) {
        scope.launch {
            delay(1500)
            updateState(appId, State.Idle)
        }
    }

    private fun updateState(appId: Int, state: State) {
        _states.value = _states.value + (appId to state)
    }

    companion object {
        const val EXTRA_APP_ID = "extra_app_id"
    }
}
