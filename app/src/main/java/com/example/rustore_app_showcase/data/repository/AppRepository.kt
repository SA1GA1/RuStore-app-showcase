package com.example.rustore_app_showcase.data.repository

import com.example.rustore_app_showcase.data.network.RuStoreApi
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo

class AppRepository(private val api: RuStoreApi) {

    suspend fun getApps(category: String? = null): List<AppInfo> {
        return try {
            api.getApps(category)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getCategories(): List<CategoryInfo> {
        return try {
            api.getCategories()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getAppDetails(id: Int): AppInfo? {
        return try {
            api.getAppById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
