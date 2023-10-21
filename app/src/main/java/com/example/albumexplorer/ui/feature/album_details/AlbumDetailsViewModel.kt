package com.example.albumexplorer.ui.feature.album_details

import androidx.lifecycle.SavedStateHandle
import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.domain.usecase.GetPhotosUseCase
import com.example.albumexplorer.ui.base.BaseViewModel
import com.example.albumexplorer.ui.base.ErrorHandler
import com.example.albumexplorer.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AlbumDetailsUiState, AlbumDetailsUiEffect>(AlbumDetailsUiState()),
    AlbumDetailsInteractionListener {
    private val args = DetailsArgs(savedStateHandle)

    init {
        _state.update {
            it.copy(
                albumName = args.albumName,
                albumID = args.albumID.toInt()
            )
        }
        getPhotos()
    }

    fun getPhotos() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                getPhotosUseCase(_state.value.albumID)
            },
            ::onGetPhotosSuccess,
            ::onGetPhotosError,
            dispatcherProvider.io
        )
    }

    private fun onGetPhotosSuccess(photos: List<Photo>) {
        _state.update { uiState ->
            uiState.copy(
                photos = photos.map { it.toUiModel() },
                filteredPhotos= photos.map { it.toUiModel() },
                isLoading = false,
                error = null,
                isError = false
            )
        }
    }

    private fun onGetPhotosError(error: ErrorHandler) {
        _state.update {
            it.copy(
                error = error, isLoading = false,
                isError = true
            )
        }
    }

    override fun onPhotoClicked(imgUrl: String) {
        effectActionExecutor(
            AlbumDetailsUiEffect.NavigateToPhoto(imgUrl)
        )
    }

    override fun onSearchTextChanged(text: String) {
        _state.update { it.copy(searchQuery = text) }
        if (text.isEmpty()) {
            _state.update { it.copy(filteredPhotos = _state.value.photos) }
            return
        }
        val photos = state.value.photos.filter { it.title.contains(text, true) }
        _state.update { it.copy(filteredPhotos = photos) }
    }
}