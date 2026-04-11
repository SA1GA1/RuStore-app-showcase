package com.example.rustore_app_showcase.data.network

import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RuStoreApi {

    // Получение всех приложений (при null)/приложений конкретной категории
    @GET("apps")
    suspend fun getApps(
        @Query("category") categoryName: String? = null
    ): List<AppInfo>

    // Получение категорий
    @GET("categories")
    suspend fun getCategories(): List<CategoryInfo>

    // Получение приложения по ID
    @GET("apps/{id}")
    suspend fun getAppById(
        @Path("id") id: Int
    ): AppInfo
}
