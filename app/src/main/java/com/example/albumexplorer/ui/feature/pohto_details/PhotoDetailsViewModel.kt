package com.example.albumexplorer.ui.feature.pohto_details

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args = PhotosDetailsArgs(savedStateHandle)
    private val photoID: String = args.photoID
    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()
    init {
        _state.value = photoID
    }
}