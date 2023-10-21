package com.example.albumexplorer.ui.feature.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.albumexplorer.ui.components.ApplicationScaffold
import com.example.albumexplorer.ui.components.ContentVisibility
import com.example.albumexplorer.ui.components.Loading
import com.example.albumexplorer.ui.feature.details.navigateToDetailsScreen
import com.example.albumexplorer.ui.navigation.LocalNavigationProvider

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Log.d("Dsfdfd", "sd $state")
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileUiEffect.NavigateToAlbumDetails -> {
                    navController.navigateToDetailsScreen(effect.albumName, effect.albumID)
                }
            }
        }
    }

    ProfileContent(state, viewModel)
}


@Composable
fun ProfileContent(
    state: ProfileUiState,
    listener: ProfileInteractionListener
) {
    ApplicationScaffold(
        textToShow = "Profile"
    ) {
        Loading(state = state.isLoading)
        ContentVisibility(state.contentScreen()) {
            Column {
                UserInfoSection(
                    state.userName,
                    state.city + ", " + state.street + ", " + state.suite + ", " + state.zipcode,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Text(
                            text = "My Albums",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    items(state.albums.size) { index ->
                        val album = state.albums[index]
                        Divider(
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable(onClick = {
                                    listener.onClickAlbumItem(album.title, album.id)
                                }),
                            text = album.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
//                AlbumList(albums = state.albums) {
//                    listener.onClickAlbumItem(state.userName, state.)
//                }
            }
        }
    }
}

@Composable
fun UserInfoSection(
    userName: String,
    address: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = address,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun AlbumList(
    albums: List<AlbumUiModel>,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "My Albums",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
        items(albums) { album ->
            Divider(
                color = MaterialTheme.colorScheme.background
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable(onClick = onClick),
                text = album.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
