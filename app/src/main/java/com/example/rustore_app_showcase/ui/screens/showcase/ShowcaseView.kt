package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    ShowcaseContent(
        selectedCategory = selectedCategory,
        apps = apps,
        allCategories = categories,
        isRefreshing = isRefreshing,
        onRefresh = viewModel::refresh,
        onAppClick = onAppClick,
        onCategoryClick = onCategoryClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseContent(
    selectedCategory: String?,
    apps: List<AppInfo>,
    allCategories: List<CategoryInfo>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onAppClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    AppCard(app = app, onClick = { onAppClick(app.id) })
                }
            } else {
                allCategories.forEach { category ->
                    val categoryApps = apps.filter { it.category == category.title }
                    item {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            SectionHeader(
                                title = category.title,
                                description = category.description,
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
