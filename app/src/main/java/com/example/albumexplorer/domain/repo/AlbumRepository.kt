package com.example.albumexplorer.domain.repo

import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.domain.model.User

interface AlbumRepository {
    suspend fun getUsers(): List<User>
    suspend fun getAlbums(id:Int):List<Album>
    suspend fun getPhotos(id: Int):List<Photo>
}