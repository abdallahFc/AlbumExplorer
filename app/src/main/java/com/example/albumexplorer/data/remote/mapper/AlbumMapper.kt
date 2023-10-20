package com.example.albumexplorer.data.remote.mapper

import com.example.albumexplorer.data.remote.model.AlbumsItemDto
import com.example.albumexplorer.domain.model.Album

fun AlbumsItemDto.toAlbum() = Album(id = id, title = title ?: "")