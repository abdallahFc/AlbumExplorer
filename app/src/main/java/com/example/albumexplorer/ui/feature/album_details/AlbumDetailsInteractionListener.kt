package com.example.albumexplorer.ui.feature.album_details

interface AlbumDetailsInteractionListener {
    fun onPhotoClicked(imgUrl: String)
    fun onSearchTextChanged(text: String)
}