package com.example.albumexplorer.ui.feature.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.albumexplorer.ui.navigation.Screen

private val ROUTE= Screen.Profile.route

fun NavGraphBuilder.profileRoute() {
    composable(ROUTE) { ProfileScreen() }
}