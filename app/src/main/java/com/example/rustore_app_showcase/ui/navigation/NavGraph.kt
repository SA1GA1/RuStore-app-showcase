package com.example.rustore_app_showcase.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rustore_app_showcase.ui.screens.appdetails.AppDetailsScreen
import com.example.rustore_app_showcase.ui.screens.categories.CategoriesScreen
import com.example.rustore_app_showcase.ui.screens.onboarding.OnboardingScreen
import com.example.rustore_app_showcase.ui.screens.screenshots.ScreenshotsScreen
import com.example.rustore_app_showcase.ui.screens.showcase.ShowcaseScreen

@Composable
fun AppNavGraph (navController: NavHostController) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("rustore_prefs", Context.MODE_PRIVATE)
    val onboardingComplete = sharedPreferences.getBoolean("onboarding_complete", false)


    NavHost (
        navController = navController,
        startDestination = if (onboardingComplete) Screen.Showcase.route else Screen.Onboarding.route
    ) {

        // отвечает за ONboarding, осуществляет логику, чтобы он не показывался более одного раза
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onContinueClick = {
                    navController.navigate(Screen.Showcase.route) {
                        popUpTo(Screen.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "showcase?category={categoryName}", // Используй константу, если она есть
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")

            ShowcaseScreen(
                selectedCategory = categoryName, // Передаем категорию в экран
                onAppClick = { appID ->
                    navController.navigate(Screen.AppDetails.createRoute(appID))
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { categoryName ->
                    // Вместо popBackStack навигируемся на витрину с фильтром
                    navController.navigate("showcase?category=$categoryName")
                }
            )
        }

        composable(
            Screen.AppDetails.route,
            arguments = listOf(navArgument("appID") { type = NavType.IntType })
        ) {

            AppDetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onInstallClick = {
                    println("Нажата кнопка установки")
                }
            )
        }

        composable(
            Screen.Screenshots.route,
            arguments = listOf(navArgument("appID") {type = NavType.IntType},
                navArgument("imageIndex") {type = NavType.IntType})) { backStackEntry ->

            val appID = backStackEntry.arguments?.getInt("appID") ?: 0
            val imageIndex = backStackEntry.arguments?.getInt("imageIndex") ?: 0

            ScreenshotsScreen(
                appID,
                imageIndex,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}