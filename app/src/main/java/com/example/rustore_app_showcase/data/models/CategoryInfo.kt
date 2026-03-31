package com.example.rustore_app_showcase.data.models

import androidx.compose.ui.graphics.Color

data class CategoryInfo(
    val id: Int,
    val title: String,
    val icon: Int,
    val appsCount: Int,
    val color: Color
)
