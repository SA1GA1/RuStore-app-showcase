package com.example.rustore_app_showcase.data.models

data class AppInfo(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val category: String,
    val rating: Double,
    val ratingCount: Int,
    val ratingPlace: Int,
    val ageRating: Int,
    val developerName: String,
    val iconUrl: String,
    val screenshots: List<String>,
    val isInstalled: Boolean,
    val size: String,
    val lastVersion: String,
    val lastVersionDescription: String
)
