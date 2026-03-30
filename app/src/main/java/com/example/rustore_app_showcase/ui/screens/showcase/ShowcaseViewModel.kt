package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.lifecycle.ViewModel
import com.example.rustore_app_showcase.data.models.AppInfo
import com.example.rustore_app_showcase.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShowcaseViewModel (
    private val repository: AppRepository = AppRepository()
): ViewModel() {
    private val _state = MutableStateFlow<List<AppInfo>>(emptyList())
    val state = _state.asStateFlow()

    private fun loadApps() {
        _state.value = repository.getApps()
    }

    init {
        loadApps()
    }
}