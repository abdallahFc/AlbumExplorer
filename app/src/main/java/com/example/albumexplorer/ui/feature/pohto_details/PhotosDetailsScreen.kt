package com.example.albumexplorer.ui.feature.pohto_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.albumexplorer.ui.components.ZoomableImage
import com.example.albumexplorer.ui.util.shareImage

@Composable
fun PhotosDetailsScreen(
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    PhotosDetailsContent(state)
}

@Composable
fun PhotosDetailsContent(
    photoUrl: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ZoomableImage(url = photoUrl, modifier = Modifier.weight(0.9f))
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.surface
            ),
            onClick = { context.shareImage(photoUrl) }) {
            Text(
                text = "Share",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

    }

}