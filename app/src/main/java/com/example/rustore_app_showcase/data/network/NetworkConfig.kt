package com.example.rustore_app_showcase.data.network

import android.os.Build

object NetworkConfig {
    // Emulator: 10.0.2.2 routes to host machine's localhost
    // Real device: host machine's LAN IP (find with `ifconfig | grep "inet 192"` on Mac)
    private const val LAN_IP = "192.168.0.6"

    private val isEmulator = Build.FINGERPRINT.startsWith("generic") ||
        Build.FINGERPRINT.startsWith("unknown") ||
        Build.MODEL.contains("Emulator") ||
        Build.MODEL.contains("Android SDK") ||
        Build.MANUFACTURER.contains("Genymotion") ||
        Build.BRAND.startsWith("generic") ||
        Build.PRODUCT.contains("sdk")

    val BASE_URL = if (isEmulator) "http://10.0.2.2:8080/" else "http://$LAN_IP:8080/"
}
