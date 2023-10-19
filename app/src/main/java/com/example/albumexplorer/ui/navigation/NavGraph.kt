package com.example.albumexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost


@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(navController = navController, startDestination = Screen.Home.route,
        ) {
//        homeRoute()
//        detailsRoute()
//        issuesRoute()
    }
}