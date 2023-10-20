package com.example.albumexplorer.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.albumexplorer.ui.navigation.Screen

private val ROUTE = Screen.Details.route

fun NavController.navigateToDetailsScreen(albumName: String, albumID: String) {
    navigate("$ROUTE/${albumName}/${albumID}")
}

fun NavGraphBuilder.detailsRoute() {
    composable(
        route = "$ROUTE/{${DetailsArgs.ALBUM_NAME}}/{${DetailsArgs.ALBUM_ID}}",
        arguments = listOf(
            navArgument(name = DetailsArgs.ALBUM_NAME) {
                NavType.StringType
            },
            navArgument(name = DetailsArgs.ALBUM_ID) {
                NavType.StringType
            }
        )
    ) {
        AlbumDetailsScreen()
    }
}

class DetailsArgs(savedStateHandle: SavedStateHandle) {
    val albumName: String = checkNotNull(savedStateHandle[ALBUM_NAME])
    val albumID: String = checkNotNull(savedStateHandle[ALBUM_ID])
    companion object {
        const val ALBUM_NAME = "albumName"
        const val ALBUM_ID = "repoName"
    }
}

