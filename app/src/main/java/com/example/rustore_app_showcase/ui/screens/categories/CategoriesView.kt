package com.example.rustore_app_showcase.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore_app_showcase.shared.CategoryInfo
import com.example.rustore_app_showcase.ui.screens.components.CategoryCard
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    viewModel: CategoriesViewModel = viewModel()
) {
    val categories by viewModel.categories.collectAsState()

    CategoriesContent(
        categories = categories,
        onCategoryClick = onCategoryClick
    )
}

@Composable
fun CategoriesContent(
    categories: List<CategoryInfo>,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {

        Text(
            text = "Категории",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .statusBarsPadding()

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

@Preview
@Composable
fun CategoriesPreview() {
    RuStoreappshowcaseTheme {
        val mockCategories = listOf(
            CategoryInfo(1, "Финансы", 0, 1245, "0xFFE8F5E9"),
            CategoryInfo(2, "Игры", 0, 8420, "0xFFE3F2FD"),
            CategoryInfo(3, "Покупки", 0, 512, "0xFFFFF3E0"),
            CategoryInfo(4, "Инструменты", 0, 3120, "0xFFE0F7FA")
        )

        CategoriesContent(
            categories = mockCategories,
            onCategoryClick = {}
        )
    }
}