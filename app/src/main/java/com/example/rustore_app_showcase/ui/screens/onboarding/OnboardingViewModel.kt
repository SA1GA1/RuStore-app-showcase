package com.example.rustore_app_showcase.ui.screens.onboarding

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.core.content.edit

class OnboardingViewModel(application: Application): AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("rustore_prefs", Context.MODE_PRIVATE)

    fun onOnboardingContinueClick() {
        sharedPreferences.edit {
            putBoolean("onboarding_complete", true)
        }
    }
}