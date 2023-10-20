package com.example.albumexplorer.ui.navigation

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Details : Screen("details")
//    object Issues : Screen("issues")
}
