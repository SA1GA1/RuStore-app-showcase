package com.example.rustore_app_showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.rustore_app_showcase.ui.navigation.AppNavGraph
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreappshowcaseTheme {
                val navController = rememberNavController()

                AppNavGraph(navController)
            }
        }
    }
}
