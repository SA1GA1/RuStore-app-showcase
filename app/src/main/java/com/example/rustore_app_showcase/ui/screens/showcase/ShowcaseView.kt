package com.example.rustore_app_showcase.ui.screens.showcase

// добавить тестовые скриншоты и тестовые иконки
// реализовать установку через PackageInstaller



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import com.example.rustore_app_showcase.ui.screens.components.AppCard
import com.example.rustore_app_showcase.ui.screens.components.HorizontalAppSection
import com.example.rustore_app_showcase.ui.screens.components.SectionHeader
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowcaseScreen(
    viewModel: ShowcaseViewModel = koinViewModel(),
    onAppClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val apps by viewModel.appsState.collectAsState()
    val categories by viewModel.categoriesState.collectAsState()
    val selectedCategory = viewModel.selectedCategory

    ShowcaseContent(
        selectedCategory = selectedCategory,
        apps = apps,
        allCategories = categories,
        onAppClick = onAppClick,
        onCategoryClick = onCategoryClick,
        onBackClick = onBackClick
    )
}

@Composable
fun ShowcaseContent(
    selectedCategory: String?,
    apps: List<AppInfo>,
    allCategories: List<CategoryInfo>,
    onAppClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            if (selectedCategory != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp, bottom = 4.dp)
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                    Text(
                        text = selectedCategory,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    )
                }
            } else {
                Text(
                    text = "RuStore",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
            }
        }

        if (selectedCategory != null) {
            items(apps) { app ->
                AppCard(app = app, onClick = { onAppClick(app.id) }, onInstallClick = {})
            }
        } else {
            // Итерируемся по ВСЕМ категориям, полученным из репозитория
            allCategories.forEach { category ->
                val categoryApps = apps.filter { it.category == category.title }

                item {
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        SectionHeader(
                            title = category.title,
                            description = category.description, // Описание из StaticData
                            onClick = { onCategoryClick(category.title) }
                        )
                        HorizontalAppSection(
                            apps = categoryApps,
                            onAppClick = onAppClick
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowcasePreview() {
    RuStoreappshowcaseTheme {
        ShowcaseContent(
            selectedCategory = null,
            apps = listOf(
                AppInfo(1, "Сбер", "Финансы", "Описание", "Финансы", 4.8, 100, 1, 6, "Sber", "", emptyList(), false, "200MB", "1.0", ""),
                AppInfo(2, "ВК", "Соцсеть", "Описание", "Инструменты", 4.5, 200, 2, 12, "VK", "", emptyList(), false, "150MB", "1.0", "")
            ),
            // ДОБАВЛЯЕМ ЭТОТ ПАРАМЕТР:
            allCategories = listOf(
                CategoryInfo(1, "Финансы", "Описание финансов", 0, 0, "0xFFD1F6D3"),
                CategoryInfo(2, "Инструменты", "Полезные утилиты", 0, 0, "0xFFAAD2D7"),
            ),
            onAppClick = {},
            onCategoryClick = {},
            onBackClick = {}
        )
    }
}