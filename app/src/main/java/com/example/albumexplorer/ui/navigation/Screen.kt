package com.example.albumexplorer.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
//    object Details : Screen("details")
//    object Issues : Screen("issues")
}
