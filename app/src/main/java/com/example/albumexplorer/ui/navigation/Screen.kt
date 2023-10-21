package com.example.albumexplorer.ui.navigation

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object AlbumDetails : Screen("album")
    object PhotosDetails : Screen("photos")
}
