package com.example.albumexplorer.data.repo

import com.example.albumexplorer.data.remote.AlbumService
import com.example.albumexplorer.domain.util.EmptyResponseException
import com.example.albumexplorer.domain.util.NetworkErrorException
import com.example.albumexplorer.domain.util.ServerErrorException
import com.example.albumexplorer.domain.util.UnknownErrorException
import com.example.albumexplorer.data.remote.mapper.toAlbum
import com.example.albumexplorer.data.remote.mapper.toPhoto
import com.example.albumexplorer.data.remote.mapper.toUser
import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.domain.model.User
import com.example.albumexplorer.domain.repo.AlbumRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val apiService: AlbumService
) : AlbumRepository {
    override suspend fun getUsers(): List<User> {
        return wrapResponse { apiService.getUsers() }.map { it.toUser() }
    }

    override suspend fun getAlbums(id: Int): List<Album> {
        return wrapResponse { apiService.getAlbums(id) }.map { it.toAlbum() }
    }

    override suspend fun getPhotos(id: Int): List<Photo> {
        return wrapResponse { apiService.getPhotos(id) }.map { it.toPhoto() }
    }

    private inline fun <reified T> wrapResponse(
        function: () -> Response<T>
    ): T {
        try {
            val response = function()
            if (response.isSuccessful) {
                val baseResponse = response.body()
                return baseResponse ?: throw EmptyResponseException()
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    throw ServerErrorException(errorBody)
                } else {
                    throw ServerErrorException(ERROR_UNKNOWN_SERVER)
                }
            }
        } catch (e: IOException) {
            throw NetworkErrorException(ERROR_NETWORK)
        } catch (e: Exception) {
            throw UnknownErrorException(ERROR_UNEXPECTED)
        }
    }

    companion object {
        const val ERROR_UNKNOWN_SERVER = "server error"
        const val ERROR_NETWORK = "No Internet Connection"
        const val ERROR_UNEXPECTED = "An unexpected error occurred"
    }
}