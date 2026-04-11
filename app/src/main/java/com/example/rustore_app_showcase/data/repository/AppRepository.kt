package com.example.rustore_app_showcase.data.repository

import com.example.rustore_app_showcase.data.network.RuStoreApi
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo

class AppRepository(private val api: RuStoreApi) {

    // Получить список приложений (или всех приложений определенной категории)
    suspend fun getApps(category: String? = null): List<AppInfo> {
        return try {
            api.getApps(category)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Получить список категорий
    suspend fun getCategories(): List<CategoryInfo> {
        return try {
            api.getCategories()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Получить данные приложения по ID
    suspend fun getAppDetails(id: Int): AppInfo? {
        return try {
            api.getAppById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}