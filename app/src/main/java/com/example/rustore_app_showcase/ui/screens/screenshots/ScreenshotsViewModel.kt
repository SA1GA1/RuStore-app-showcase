package com.example.rustore_app_showcase.ui.screens.screenshots

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rustore_app_showcase.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScreenshotsViewModel (
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val repository = AppRepository()

    private val appID: Int = checkNotNull(savedStateHandle["appID"])

    private val _screenshots = MutableStateFlow<List<String>>(emptyList())

    val screenshots = _screenshots.asStateFlow()

    init {
        loadScreenshots()
    }

    private fun loadScreenshots() {
        val app = repository.getApps().find { it.id == appID }
        _screenshots.value = app?.screenshots ?: emptyList()

    }
}