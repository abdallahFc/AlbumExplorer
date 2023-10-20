package com.example.albumexplorer.domain.usecase

import com.example.albumexplorer.data.remote.EmptyResponseException
import com.example.albumexplorer.domain.model.User
import com.example.albumexplorer.domain.repo.AlbumRepository
import javax.inject.Inject

class GetRandomUserUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(): User {
        val users = repository.getUsers()
        if (users.isEmpty()) {
            throw EmptyResponseException()
        }
        return users.random()
    }
}
