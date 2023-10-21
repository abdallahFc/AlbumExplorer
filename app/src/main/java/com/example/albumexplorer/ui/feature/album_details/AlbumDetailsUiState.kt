package com.example.albumexplorer.ui.feature.album_details

import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.ui.base.ErrorHandler

data class AlbumDetailsUiState(
    val isLoading: Boolean = false,
    val albumID: Int = 0,
    val photos: List<PhotoUiModel> = emptyList(),
    val filteredPhotos: List<PhotoUiModel> = emptyList(),
    val albumName: String = "",
    val searchQuery: String = "",
    val isError: Boolean = false,
    val error: ErrorHandler? = null
)

data class PhotoUiModel(
    val title: String = "",
    val imgUrl: String = "",
    val thumbnailUrl: String = ""
)

fun Photo.toUiModel() = PhotoUiModel(
    title = title,
    imgUrl = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumDetailsUiState.contentScreen() = !this.isLoading && !this.isError
fun AlbumDetailsUiState.emptyContent() = !this.isLoading && !this.isError && this.filteredPhotos.isEmpty()
