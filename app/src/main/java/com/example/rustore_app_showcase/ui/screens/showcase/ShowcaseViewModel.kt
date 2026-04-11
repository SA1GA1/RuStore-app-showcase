package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowcaseViewModel(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val selectedCategory: String? = savedStateHandle["categoryName"]

    private val _state = MutableStateFlow<List<AppInfo>>(emptyList())
    val state = _state.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            val apps = repository.getApps(selectedCategory)
            _state.value = apps
        }
    }
}