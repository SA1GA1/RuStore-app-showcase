package com.example.rustore_app_showcase.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.data.models.AppInfo
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

@Composable
fun HorizontalAppSection(
    apps: List<AppInfo>,
    onAppClick: (Int) -> Unit
) {
    val chunkedApps = apps.chunked(3)

    LazyRow (contentPadding = PaddingValues(horizontal = 4.dp)) {
        items(chunkedApps) { columnApps ->
            Column(
                modifier = Modifier.width(330.dp)
            ) {
                columnApps.forEach { app ->
                    AppCard(
                        app = app,
                        onClick = { onAppClick(app.id) },
                        onInstallClick = { }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun HorizontalAppSectionPreview() {
    RuStoreappshowcaseTheme {
        val mockApps = List(6) { index ->
            AppInfo(
                id = index,
                title = "Приложение $index",
                shortDescription = "Описание приложения $index",
                fullDescription = "",
                category = "Категория",
                rating = 4.5,
                ageRating = 12,
                developerName = "Developer",
                iconUrl = "",
                screenshots = emptyList(),
                isInstalled = false,
                size = "50 MB",
                lastVersion = "1.0",
                lastVersionDescription = ""
            )
        }

        HorizontalAppSection(
            apps = mockApps,
            onAppClick = {}
        )
    }
}