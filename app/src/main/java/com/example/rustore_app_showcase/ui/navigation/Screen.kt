package com.example.rustore_app_showcase.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Showcase : Screen("showcase?category={categoryName}") {
        fun createRoute(categoryName : String?) = if (categoryName != null) "showcase?category=$categoryName" else "showcase"
    }
    object Categories : Screen("categories")
    object AppDetails : Screen("app_details/{appID}") {
        fun createRoute(appID : Int) = "app_details/${appID}"
    }
    object Screenshots : Screen("screenshots/{appID}/{imageIndex}") {
        fun createRoute(appID : Int, imageIndex : Int) = "screenshots/${appID}/${imageIndex}"
    }
}