package com.example.albumexplorer.data.remote

import com.example.albumexplorer.data.remote.model.UsersDto
import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {
    @GET("/users")
    suspend fun getUsers(): Response<List<UsersDto>>
}