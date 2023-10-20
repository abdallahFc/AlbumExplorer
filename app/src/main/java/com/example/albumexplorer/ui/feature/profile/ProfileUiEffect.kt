package com.example.albumexplorer.ui.feature.profile

import com.example.albumexplorer.ui.base.BaseUiEffect


sealed class ProfileUiEffect: BaseUiEffect {
    data class NavigateToAlbumDetails(val albumName: String, val albumID: String) : ProfileUiEffect()
}
