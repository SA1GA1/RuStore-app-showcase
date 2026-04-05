package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rustore_app_showcase.data.models.AppInfo
import com.example.rustore_app_showcase.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShowcaseViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = AppRepository()
    private val selectedCategory: String? = savedStateHandle["categoryName"]

    private val _state = MutableStateFlow<List<AppInfo>>(emptyList())
    val state = _state.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _state.value = repository.getAppsByCategory(selectedCategory)
    }
}