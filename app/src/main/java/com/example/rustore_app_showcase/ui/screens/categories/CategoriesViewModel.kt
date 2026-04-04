package com.example.rustore_app_showcase.ui.screens.categories

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.rustore_app_showcase.data.models.CategoryInfo
import com.example.rustore_app_showcase.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel : ViewModel() {
    private val repository = AppRepository()

    private val _categories = MutableStateFlow<List<CategoryInfo>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = listOf(
            CategoryInfo(1, "Финансы", 0, 1245, Color(0xFFD1F6D3)),
            CategoryInfo(2, "Игры", 0, 8420, Color(0xFFA6D1EF)),
            CategoryInfo(3, "Покупки", 0, 512, Color(0xFFD7BFA3)),
            CategoryInfo(4, "Инструменты", 0, 3120, Color(0xFFAAD2D7)),
            CategoryInfo(5, "Образование", 0, 945, Color(0xFF747BA6)),
            CategoryInfo(6, "Социальные", 0, 218, Color(0xFFEFE8A0)),
            CategoryInfo(7, "Развлечения", 0, 1890, Color(0xFFEFCBF5))
        )
    }
}