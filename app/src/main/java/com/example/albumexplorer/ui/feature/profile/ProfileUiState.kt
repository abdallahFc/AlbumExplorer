package com.example.albumexplorer.ui.feature.profile

import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.ui.base.ErrorHandler

data class ProfileUiState(
    val isLoading: Boolean = false,
    val id:String="",
    val userName:String="",
    val city: String="",
    val street: String="",
    val suite: String="",
    val zipcode: String="",
    val albums:List<AlbumUiModel> = emptyList(),
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
)
data class AlbumUiModel(
    val id: String="",
    val title: String=""
)
fun Album.toUiModel()=AlbumUiModel(id=id.toString(),title=title)
fun ProfileUiState.contentScreen() = !this.isLoading && !this.isError