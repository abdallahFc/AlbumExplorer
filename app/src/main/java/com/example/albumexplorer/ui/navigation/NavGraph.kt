package com.example.albumexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.albumexplorer.ui.feature.details.detailsRoute
import com.example.albumexplorer.ui.feature.profile.profileRoute


@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(
        navController = navController, startDestination = Screen.Profile.route,
    ) {
        profileRoute()
//        homeRoute()
        detailsRoute()
//        issuesRoute()
    }
}