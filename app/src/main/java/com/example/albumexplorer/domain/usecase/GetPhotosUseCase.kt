package com.example.albumexplorer.domain.usecase

import com.example.albumexplorer.data.remote.EmptyResponseException
import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.domain.repo.AlbumRepository
import javax.inject.Inject

class GetPhotosUseCase  @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(albumId: Int): List<Photo> {
        val photos = repository.getPhotos(albumId)
        if (photos.isEmpty()) {
            throw EmptyResponseException()
        }
        return photos
    }
}