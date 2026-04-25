package com.example.rustore_app_showcase.di

import com.example.rustore_app_showcase.data.install.InstallManager
import com.example.rustore_app_showcase.data.network.NetworkConfig
import com.example.rustore_app_showcase.data.network.RuStoreApi
import com.example.rustore_app_showcase.data.repository.AppRepository
import com.example.rustore_app_showcase.ui.screens.appdetails.AppDetailsViewModel
import com.example.rustore_app_showcase.ui.screens.categories.CategoriesViewModel
import com.example.rustore_app_showcase.ui.screens.onboarding.OnboardingViewModel
import com.example.rustore_app_showcase.ui.screens.screenshots.ScreenshotsViewModel
import com.example.rustore_app_showcase.ui.screens.showcase.ShowcaseViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RuStoreApi::class.java)
    }

    singleOf(::AppRepository)
    single { InstallManager(androidContext()) }

    viewModelOf(::CategoriesViewModel)
    viewModelOf(::ShowcaseViewModel)
    viewModelOf(::AppDetailsViewModel)
    viewModelOf(::ScreenshotsViewModel)
    viewModelOf(::OnboardingViewModel)
}