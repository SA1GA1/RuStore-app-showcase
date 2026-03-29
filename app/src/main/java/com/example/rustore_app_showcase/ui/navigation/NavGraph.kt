package com.example.rustore_app_showcase.ui.navigation

import androidx.compose.runtime.Composable
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
    NavHost (
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen(onContinueClick = {navController.navigate(Screen.Showcase.route)})
        }

        composable(Screen.Showcase.route) {
            ShowcaseScreen()
        }

        composable(Screen.Categories.route) {
            CategoriesScreen()
        }

        composable(
            Screen.AppDetails.route,
            arguments = listOf(navArgument("appID") { type = NavType.IntType})) { backStackEntry ->

            val appID = backStackEntry.arguments?.getInt("appID") ?: 0

            AppDetailsScreen(appID)
        }

        composable(
            Screen.Screenshots.route,
            arguments = listOf(navArgument("appID") {type = NavType.IntType},
                navArgument("imageIndex") {type = NavType.IntType})) { backStackEntry ->

            val appID = backStackEntry.arguments?.getInt("appID") ?: 0
            val imageIndex = backStackEntry.arguments?.getInt("imageIndex") ?: 0

            ScreenshotsScreen(appID, imageIndex)
        }
    }
}