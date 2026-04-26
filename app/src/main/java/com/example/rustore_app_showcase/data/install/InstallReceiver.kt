package com.example.rustore_app_showcase.data.install

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import org.koin.java.KoinJavaComponent

class InstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE)
        val appId = intent.getIntExtra(InstallManager.EXTRA_APP_ID, -1)
        val installManager: InstallManager = KoinJavaComponent.get(InstallManager::class.java)

        when (status) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                @Suppress("DEPRECATION")
                val confirmIntent = intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
                confirmIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                confirmIntent?.let { context.startActivity(it) }
            }
            PackageInstaller.STATUS_SUCCESS -> {
                if (appId != -1) installManager.onInstallSuccess(appId)
            }
            else -> {
                // STATUS_FAILURE, STATUS_FAILURE_ABORTED, STATUS_FAILURE_INVALID_APK и т.д.
                if (appId != -1) installManager.onInstallFailure(appId)
            }
        }
    }
}
