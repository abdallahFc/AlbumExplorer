package com.example.albumexplorer.ui.feature.pohto_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.albumexplorer.ui.navigation.Screen


private val ROUTE = Screen.PhotosDetails.route

fun NavController.navigateToPhotosDetailsScreen(photoID: String) {
    navigate("$ROUTE/${photoID}")
}
fun NavGraphBuilder.photosDetailsRoute() {
    composable(
        route = "$ROUTE/{${PhotosDetailsArgs.PHOTO_ID}}",
        arguments = listOf(
            navArgument(name = PhotosDetailsArgs.PHOTO_ID) {
                type = NavType.StringType
            }
        )
    ) {
        PhotosDetailsScreen()
    }
}
class PhotosDetailsArgs(savedStateHandle: SavedStateHandle) {
    val photoID: String = checkNotNull(savedStateHandle[PHOTO_ID])
    companion object {
        const val PHOTO_ID = "photoID"
    }
}