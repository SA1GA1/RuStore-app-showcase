package com.example.rustore_app_showcase.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.shared.CategoryInfo
import com.example.rustore_app_showcase.ui.screens.components.CategoryCard
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    CategoriesContent(
        categories = categories,
        isRefreshing = isRefreshing,
        onRefresh = viewModel::refresh,
        onCategoryClick = onCategoryClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesContent(
    categories: List<CategoryInfo>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onCategoryClick: (String) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Категории",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                fontWeight = FontWeight.Bold,
                color = MainColor
            )

            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val rows = categories.chunked(2)

                items(rows) { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (category in rowItems) {
                            Box(modifier = Modifier.weight(1f)) {
                                CategoryCard(
                                    category = category,
                                    onClick = { onCategoryClick(category.title) }
                                )
                            }
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoriesPreview() {
    RuStoreappshowcaseTheme {
        val mockCategories = listOf(
            CategoryInfo(1, "Финансы", "", 1245, 5, "0xFFD1F6D3"),
            CategoryInfo(2, "Игры", "", 8420, 5, "0xFFE3F2FD"),
            CategoryInfo(3, "Покупки", "", 512, 10, "0xFFFFF3E0"),
            CategoryInfo(4, "Инструменты", "", 3120, 10, "0xFFE0F7FA")
        )

        CategoriesContent(
            categories = mockCategories,
            onCategoryClick = {}
        )
    }
}
