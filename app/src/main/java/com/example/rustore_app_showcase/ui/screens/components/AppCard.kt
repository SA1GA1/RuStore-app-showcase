package com.example.rustore_app_showcase.ui.screens.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.data.install.InstallManager
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.ui.theme.LightBlue
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.compose.koinInject

@Composable
fun AppCard(
    app: AppInfo,
    onClick: () -> Unit,
    installManager: InstallManager = koinInject()
) {
    val installStates by installManager.states.collectAsState()
    val installState = installStates[app.id] ?: InstallManager.State.Idle
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (context.packageManager.canRequestPackageInstalls()) {
            installManager.install(app.id, app.apkUrl)
        }
    }

    fun handleInstallClick() {
        when (installState) {
            InstallManager.State.Installed -> installManager.uninstall(app.id)
            InstallManager.State.Idle -> {
                if (context.packageManager.canRequestPackageInstalls()) {
                    installManager.install(app.id, app.apkUrl)
                } else {
                    permissionLauncher.launch(
                        Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                            data = Uri.parse("package:${context.packageName}")
                        }
                    )
                }
            }
            else -> {}
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = app.iconUrl,
                contentDescription = "AppLogo",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(56.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = app.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = app.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val isInstalled = installState == InstallManager.State.Installed
            val isBusy = installState == InstallManager.State.Downloading ||
                installState == InstallManager.State.Installing

            Button(
                onClick = ::handleInstallClick,
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isInstalled) Color(0xFFCCCCCC) else MainColor,
                    disabledContainerColor = MainColor.copy(alpha = 0.6f)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = !isBusy
            ) {
                val label = when (installState) {
                    InstallManager.State.Idle -> "Установить"
                    InstallManager.State.Downloading -> "Загрузка..."
                    InstallManager.State.Installing -> "Установка..."
                    InstallManager.State.Installed -> "Удалить"
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = if (isInstalled) Color.DarkGray else Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppCardPreview() {
    RuStoreappshowcaseTheme {
        val mockApp = AppInfo(
            id = 1,
            title = "VK Мессенджер",
            shortDescription = "Общение, видеозвонки",
            fullDescription = "",
            category = "Социальные сети",
            rating = 4.8,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 12,
            developerName = "VK.com",
            iconUrl = "",
            screenshots = emptyList(),
            isInstalled = false,
            size = "150 МБ",
            lastVersion = "1.0",
            lastVersionDescription = ""
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = LightBlue),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = mockApp.title, style = MaterialTheme.typography.titleSmall)
                    Text(text = mockApp.shortDescription, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Button(
                    onClick = {},
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Установить", fontSize = 12.sp)
                }
            }
        }
    }
}
