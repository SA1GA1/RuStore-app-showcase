package com.example.rustore_app_showcase.ui.screens.screenshots

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScreenshotsViewModel (
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val appID: Int = checkNotNull(savedStateHandle["appID"])
    private val _screenshots = MutableStateFlow<List<String>>(emptyList())
    val screenshots = _screenshots.asStateFlow()

    init {
        loadScreenshots()
    }

    private fun loadScreenshots() {
        viewModelScope.launch {
            val app = repository.getAppDetails(appID)
            _screenshots.value = app?.screenshots ?: emptyList()
        }
    }
}