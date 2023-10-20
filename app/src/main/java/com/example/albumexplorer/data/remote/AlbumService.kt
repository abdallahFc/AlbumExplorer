package com.example.albumexplorer.data.remote

import com.example.albumexplorer.data.remote.model.AlbumsItemDto
import com.example.albumexplorer.data.remote.model.PhotosItemDto
import com.example.albumexplorer.data.remote.model.UsersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {
    @GET("/users")
    suspend fun getUsers(): Response<List<UsersDto>>
    @GET("/albums")
    suspend fun getAlbums(@Query("userId") userId: Int):Response<List<AlbumsItemDto>>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): Response<List<PhotosItemDto>>
}
