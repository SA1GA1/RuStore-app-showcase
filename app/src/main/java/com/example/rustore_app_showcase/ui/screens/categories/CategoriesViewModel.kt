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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        loadCategories(showRefreshing = false)
    }

    fun refresh() {
        loadCategories(showRefreshing = true)
    }

    private fun loadCategories(showRefreshing: Boolean) {
        viewModelScope.launch {
            if (showRefreshing) _isRefreshing.value = true
            try {
                _categories.value = repository.getCategories()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
