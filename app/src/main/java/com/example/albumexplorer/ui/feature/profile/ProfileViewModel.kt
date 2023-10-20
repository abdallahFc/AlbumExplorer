package com.example.albumexplorer.ui.feature.profile

import android.util.Log
import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.domain.model.User
import com.example.albumexplorer.domain.usecase.GetAlbumsUseCase
import com.example.albumexplorer.domain.usecase.GetRandomUserUseCase
import com.example.albumexplorer.ui.base.BaseUiEffect
import com.example.albumexplorer.ui.base.BaseViewModel
import com.example.albumexplorer.ui.base.ErrorHandler
import com.example.albumexplorer.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getRandomUser: GetRandomUserUseCase,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()),ProfileInteractionListener {
    init {
        getUsers()
    }

    fun getUsers() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getRandomUser() },
            ::onGetUserSuccess,
            ::onGetUser,
            dispatcherProvider.io
        )
    }

    private fun onGetUserSuccess(user: User) {
        Log.d("Dsfdfd", "sds $user")
        _state.update {
            it.copy(
                id = user.id.toString(),
                userName = user.username,
                city = user.address.city,
                zipcode = user.address.zipcode,
                street = user.address.street,
                suite = user.address.suite
            )
        }
        getAlbums(user.id)
    }

    private fun onGetUser(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true, isLoading = false) }
    }

    private fun getAlbums(id: Int) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAlbumsUseCase(id) },
            ::onGetAlbumsSuccess,
            ::onGetAlbumsError,
            dispatcherProvider.io
        )
    }

    private fun onGetAlbumsSuccess(albums: List<Album>) {
        Log.d("Dsfdfd", "sds $albums")
        _state.update {
            it.copy(
                albums = albums.map { it.toUiModel() },
                isLoading = false,
                isError = false,
                error = null
            )
        }
    }

    private fun onGetAlbumsError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true, isLoading = false) }
    }

    override fun onClickAlbumItem(albumName: String, albumID: String) {
        effectActionExecutor(
            ProfileUiEffect.NavigateToAlbumDetails(albumName, albumID)
        )
    }
}