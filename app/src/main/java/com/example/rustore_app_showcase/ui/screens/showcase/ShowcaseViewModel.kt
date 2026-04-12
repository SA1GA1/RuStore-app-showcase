package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class ShowcaseViewModel(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val selectedCategory: String? = savedStateHandle["categoryName"]

    private val _appsState = MutableStateFlow<List<AppInfo>>(emptyList())
    val appsState = _appsState.asStateFlow()

    private val _categoriesState = MutableStateFlow<List<CategoryInfo>>(emptyList())
    val categoriesState = _categoriesState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val appsDeferred = async { repository.getApps(selectedCategory) }
            val catsDeferred = async { repository.getCategories() }

            _appsState.value = appsDeferred.await()
            _categoriesState.value = catsDeferred.await()
        }
    }
}