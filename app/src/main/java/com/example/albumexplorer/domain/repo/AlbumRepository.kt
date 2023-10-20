package com.example.albumexplorer.domain.repo

import com.example.albumexplorer.domain.model.User

interface AlbumRepository {
    suspend fun getUsers(): List<User>
}