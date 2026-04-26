package com.example.rustore_app_showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rustore_app_showcase.ui.navigation.AppNavGraph
import com.example.rustore_app_showcase.ui.navigation.Screen
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBarItemDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreappshowcaseTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val selectedCategory = navBackStackEntry?.arguments?.getString("categoryName")
                val showBottomBar = (currentRoute == Screen.Showcase.route && selectedCategory == null) ||
                    currentRoute == Screen.Categories.route

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = Color.White,
                                windowInsets = WindowInsets.navigationBars,
                                tonalElevation = 0.dp
                            ) {
                                val navItemColors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MainColor,
                                    selectedTextColor = MainColor,
                                    indicatorColor = MainColor.copy(alpha = 0.12f),
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )

                                NavigationBarItem(
                                    selected = currentRoute == Screen.Showcase.route,
                                    onClick = {
                                        if (currentRoute != Screen.Showcase.route) {
                                            navController.navigate(Screen.Showcase.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    label = { Text("Приложения", fontSize = 10.sp) },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    colors = navItemColors
                                )

                                NavigationBarItem(
                                    selected = currentRoute == Screen.Categories.route,
                                    onClick = {
                                        if (currentRoute != Screen.Categories.route) {
                                            navController.navigate(Screen.Categories.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    label = { Text("Категории", fontSize = 10.sp) },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    colors = navItemColors
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
