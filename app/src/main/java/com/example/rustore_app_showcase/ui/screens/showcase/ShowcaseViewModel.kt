package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowcaseViewModel(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val selectedCategory: String? = savedStateHandle["categoryName"]

    private val _appsState = MutableStateFlow<List<AppInfo>>(emptyList())
    val appsState = _appsState.asStateFlow()

    private val _categoriesState = MutableStateFlow<List<CategoryInfo>>(emptyList())
    val categoriesState = _categoriesState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        loadData(showRefreshing = false)
    }

    fun refresh() {
        loadData(showRefreshing = true)
    }

    private fun loadData(showRefreshing: Boolean) {
        viewModelScope.launch {
            if (showRefreshing) _isRefreshing.value = true
            try {
                val appsDeferred = async { repository.getApps(selectedCategory) }
                val catsDeferred = async { repository.getCategories() }
                _appsState.value = appsDeferred.await()
                _categoriesState.value = catsDeferred.await()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
