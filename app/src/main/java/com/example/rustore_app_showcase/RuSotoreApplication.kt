package com.example.rustore_app_showcase

import android.app.Application
import com.example.rustore_app_showcase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RuStoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // запускается 1 раз на весь жизненный цикл приложения
        startKoin {
            androidContext(this@RuStoreApplication)
            modules(appModule)
        }
    }
}