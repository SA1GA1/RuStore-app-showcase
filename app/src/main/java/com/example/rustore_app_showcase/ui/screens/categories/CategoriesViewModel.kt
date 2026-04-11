package com.example.rustore_app_showcase.ui.screens.categories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.CategoryInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryInfo>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }
}