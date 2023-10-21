package com.example.albumexplorer.ui.feature.album_details

import com.example.albumexplorer.ui.base.BaseUiEffect

sealed class AlbumDetailsUiEffect: BaseUiEffect {
    data class NavigateToPhoto(val imgUrl:String) : AlbumDetailsUiEffect()
}