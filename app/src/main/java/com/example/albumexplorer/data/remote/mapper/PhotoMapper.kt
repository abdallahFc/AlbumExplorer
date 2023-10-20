package com.example.albumexplorer.data.remote.mapper

import com.example.albumexplorer.data.remote.model.PhotosItemDto
import com.example.albumexplorer.domain.model.Photo

fun PhotosItemDto.toPhoto() = Photo(
    albumId = albumId,
    id = id,
    title = title ?: "",
    thumbnailUrl = thumbnailUrl ?: "https://via.placeholder.com/150/92c952",
    url = url ?: "https://via.placeholder.com/600/92c952"
)
