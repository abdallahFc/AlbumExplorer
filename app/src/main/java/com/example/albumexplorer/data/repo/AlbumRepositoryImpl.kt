package com.example.albumexplorer.data.repo

import com.example.albumexplorer.data.remote.AlbumService
import com.example.albumexplorer.data.remote.EmptyResponseException
import com.example.albumexplorer.data.remote.NetworkErrorException
import com.example.albumexplorer.data.remote.ServerErrorException
import com.example.albumexplorer.data.remote.UnknownErrorException
import com.example.albumexplorer.data.remote.mapper.toUser
import com.example.albumexplorer.domain.model.User
import com.example.albumexplorer.domain.repo.AlbumRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val apiService: AlbumService
) : AlbumRepository {
    override suspend fun getUsers(): List<User> {
        return wrapResponse { apiService.getUsers() }.map { it.toUser()}
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