package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import com.example.rustore_app_showcase.ui.screens.components.CategoryCard
import com.example.rustore_app_showcase.ui.screens.components.HorizontalAppSection
import com.example.rustore_app_showcase.ui.screens.components.SectionHeader
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun ShowcaseScreen(
    selectedCategory: String?,
    viewModel: ShowcaseViewModel = koinViewModel(),
    onAppClick: (Int) -> Unit
) {
    val apps by viewModel.state.collectAsState()

    ShowcaseContent(
        apps = apps,
        onAppClick = onAppClick
    )
}


@Composable
fun ShowcaseContent(
    apps: List<AppInfo>,
    onAppClick: (Int) -> Unit
) {

    // FIXME: тестовые данные, удалить!
    val categories = listOf(
        CategoryInfo(1, "Игры", 0, 8420, "0xFFE3F2FD"),
        CategoryInfo(2, "Покупки", 0, 512, "0xFFFFF3E0"),
        CategoryInfo(3, "Развлечения", 0, 1890, "0xFFF3E5F5"),
        CategoryInfo(4, "Финансы", 0, 1245, "0xFFE8F5E9")
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "RuStore",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MainColor
            )
        }

        item {
            SectionHeader(
                title = "Социальные сети",
                description = "Будьте всегда на связи",
                onClick = { }
            )
        }

        // тестовая фильтрация
        item {
            HorizontalAppSection(apps = apps, onAppClick = onAppClick)
        }

        item {
            SectionHeader(
                title = "Популярные категории",
                description = "Самое интересное",
                onClick = { }
            )
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box (modifier = Modifier.weight(1f)) {
                        CategoryCard(category = categories[0], onClick = {})
                    }
                    Box (modifier = Modifier.weight(1f)) {
                        CategoryCard(category = categories[1], onClick = {})
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box (modifier = Modifier.weight(1f)) {
                        CategoryCard(category = categories[2], onClick = {})
                    }
                    Box (modifier = Modifier.weight(1f)) {
                        CategoryCard(category = categories[3], onClick = {})
                    }
                }
            }
        }


        item {
            SectionHeader(
                title = "Эффективность",
                description = "Работайте быстрее и умнее",
                onClick = { }
            )
        }

        // тоже тестовая проверка
        item {
            HorizontalAppSection(apps = apps, onAppClick = onAppClick)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowcasePreview() {
    RuStoreappshowcaseTheme {
        val mockApps = List(10) { index ->
            AppInfo(
                id = index,
                title = if (index < 5) "Социалка $index" else "Инструмент $index",
                shortDescription = "Описание $index",
                fullDescription = "",
                category = if (index < 5) "Социальные сети" else "Инструменты",
                rating = 4.5,
                ratingCount = 100,
                ratingPlace = 1,
                ageRating = 12,
                developerName = "Dev",
                iconUrl = "",
                screenshots = emptyList(),
                isInstalled = false,
                size = "10 MB",
                lastVersion = "1.0",
                lastVersionDescription = ""
            )
        }

        ShowcaseContent(
            apps = mockApps,
            onAppClick = {}
        )
    }
}

