package com.example.rustore_app_showcase.shared

import kotlinx.serialization.Serializable

@Serializable
data class CategoryInfo(
    val id: Int,
    val title: String,
    val icon: Int,
    val appsCount: Int,
    val color: String
)