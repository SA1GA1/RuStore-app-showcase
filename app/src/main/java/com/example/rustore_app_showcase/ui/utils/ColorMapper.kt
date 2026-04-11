package com.example.rustore_app_showcase.ui.utils

import androidx.compose.ui.graphics.Color

fun String.toComposeColor(): Color {
    return try {
        val hex = this.replace("0x", "").replace("#", "")
        Color(hex.toLong(16))
    } catch (e: Exception) {
        Color.Gray
    }
}