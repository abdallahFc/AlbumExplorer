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
    private val getRandomUser: GetRandomUserUseCase,
    private val getAlbumsUseCase: GetAlbumsUseCase,
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
            ::onGetUserError,
            dispatcherProvider.io
        )
    }

    private fun onGetUserSuccess(user: User) {
        _state.update {
            it.copy(
                userName = user.username,
                city = user.address.city,
                zipcode = user.address.zipcode,
                street = user.address.street,
                suite = user.address.suite
            )
        }
        getAlbums(user.id)
    }

    private fun onGetUserError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true, isLoading = false) }
    }

    fun getAlbums(id: Int) {
        tryToExecute(
            { getAlbumsUseCase(id) },
            ::onGetAlbumsSuccess,
            ::onGetAlbumsError,
            dispatcherProvider.io
        )
    }

    private fun onGetAlbumsSuccess(albums: List<Album>) {
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