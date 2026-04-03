package com.example.rustore_app_showcase.ui.screens.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.R
import com.example.rustore_app_showcase.data.models.AppInfo
import com.example.rustore_app_showcase.ui.theme.LightBlue
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

@Composable
fun AppCard(
    app: AppInfo,
    onClick: () -> Unit,
    onInstallClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBlue
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // загрушка вместо иконки приложения
                contentDescription = "AppLogo",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(56.dp)
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
                    overflow = TextOverflow.Ellipsis // троеточие
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

            Button(
                onClick = onInstallClick,
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Установить",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    maxLines = 1
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
            title = "мессенджер",
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
            size = "150 MB",
            lastVersion = "1.0",
            lastVersionDescription = ""
        )

        AppCard(app = mockApp, onClick = {}, onInstallClick = {})
    }
}