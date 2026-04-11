package com.example.rustore_app_showcase.ui.screens.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.shared.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppDetailsViewModel (
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val appId: Int = checkNotNull(savedStateHandle["appID"])

    private val _state = MutableStateFlow<AppInfo?>(null)
    val state = _state.asStateFlow()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _state.value = repository.getAppDetails(appId)
        }
    }
}