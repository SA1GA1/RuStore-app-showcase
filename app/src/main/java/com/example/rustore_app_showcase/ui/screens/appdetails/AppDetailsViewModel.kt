package com.example.rustore_app_showcase.ui.screens.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.find

class AppDetailsViewModel (
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val repository = AppRepository()

    private val appId: Int = checkNotNull(savedStateHandle["appID"])

    private val _state = MutableStateFlow<AppInfo?>(null)

    val state = _state.asStateFlow()

    init {
        _state.value = repository.getApps().find{it.id == appId}
    }
}