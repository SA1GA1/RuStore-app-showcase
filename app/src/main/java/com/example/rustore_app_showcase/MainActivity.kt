package com.example.rustore_app_showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rustore_app_showcase.ui.navigation.AppNavGraph
import com.example.rustore_app_showcase.ui.navigation.Screen
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreappshowcaseTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf(Screen.Showcase.route, Screen.Categories.route)

                AppNavGraph(navController)
            }
        }
    }
}
