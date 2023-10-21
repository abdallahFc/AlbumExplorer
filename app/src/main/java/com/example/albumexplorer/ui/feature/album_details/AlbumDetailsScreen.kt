package com.example.albumexplorer.ui.feature.album_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.albumexplorer.R
import com.example.albumexplorer.ui.components.ApplicationErrors
import com.example.albumexplorer.ui.components.ApplicationScaffold
import com.example.albumexplorer.ui.components.ContentVisibility
import com.example.albumexplorer.ui.components.EmptyView
import com.example.albumexplorer.ui.components.ImageNetwork
import com.example.albumexplorer.ui.components.Loading
import com.example.albumexplorer.ui.feature.pohto_details.navigateToPhotosDetailsScreen
import com.example.albumexplorer.ui.navigation.LocalNavigationProvider
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AlbumDetailsScreen(
    viewModel: AlbumDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AlbumDetailsUiEffect.NavigateToPhoto -> {
                    val encodedUrl = URLEncoder.encode(effect.imgUrl, StandardCharsets.UTF_8.toString())
                    navController.navigateToPhotosDetailsScreen(encodedUrl)
                }
            }
        }
    }
    AlbumDetailsContent(state, viewModel)
}

@Composable
fun AlbumDetailsContent(
    state: AlbumDetailsUiState,
    listener: AlbumDetailsInteractionListener
) {
    ApplicationScaffold(textToShow = state.albumName) {
        Loading(state = state.isLoading)
        ApplicationErrors(isError = state.isError, error = state.error)
        Column {
            SearchFiled(
                hint = "Search",
                text = state.searchQuery,
                onValueChange = { listener.onSearchTextChanged(it) },
                iconPainter = painterResource(id = R.drawable.search)
            )
            EmptyView(state = state.emptyContent())
            ContentVisibility(state.contentScreen()) {
                LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3)) {
                    items(state.filteredPhotos.size) { index ->
                        ImageNetwork(imageUrl = state.filteredPhotos[index].thumbnailUrl) {
                            listener.onPhotoClicked(state.filteredPhotos[index].imgUrl)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFiled(
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Search
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    text: String = ""
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                end = 16.dp,
                start = 16.dp,
                bottom = 16.dp,
            )
            .height(56.dp),
        value = text,
        textStyle = MaterialTheme.typography.bodySmall,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(16.dp),
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        label = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            iconPainter?.let {
                Image(
                    painter = it,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchFiled() {
    SearchFiled(
        hint = "Search",
        onValueChange = {},
        iconPainter = painterResource(id = R.drawable.search)
    )
}
