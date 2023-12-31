package com.example.albumexplorer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.albumexplorer.R

@Composable
fun ImageNetwork(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    onImageClick: () -> Unit = {}
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        error= painterResource(R.drawable.placeholder),
        placeholder = painterResource(R.drawable.loading),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.fillMaxWidth().clickable(onClick = onImageClick)
    )


}