package com.example.albumexplorer.domain.usecase

import com.example.albumexplorer.data.remote.EmptyResponseException
import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.domain.repo.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(userId: Int): List<Album> {
        val albums = repository.getAlbums(userId)
        if (albums.isEmpty()) {
            throw EmptyResponseException()
        }
        return albums
    }
}
