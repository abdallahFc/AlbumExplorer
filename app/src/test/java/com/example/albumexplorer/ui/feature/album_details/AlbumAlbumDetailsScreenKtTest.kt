package com.example.albumexplorer.ui.feature.album_details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.albumexplorer.domain.util.EmptyResponseException
import com.example.albumexplorer.domain.util.NetworkErrorException
import com.example.albumexplorer.domain.model.Photo
import com.example.albumexplorer.domain.usecase.GetPhotosUseCase
import com.example.albumexplorer.ui.base.ErrorHandler
import com.example.albumexplorer.ui.util.MainCoroutineRule
import com.example.albumexplorer.ui.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AlbumAlbumDetailsScreenKtTest {
    private lateinit var getPhotosUseCase: GetPhotosUseCase
    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var testDispatcher: TestDispatchers

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testDispatcher = TestDispatchers()
        getPhotosUseCase = mockk(relaxed = true)
        savedStateHandle = mapOf(
            "albumName" to "albumName",
            "albumID" to "1"
        ).let { SavedStateHandle(it) }
        viewModel = AlbumDetailsViewModel(getPhotosUseCase, testDispatcher, savedStateHandle)
    }

    @Test
    fun `getPhotos() when start call, then should return loading state`() = runTest {
        // Given
        val albumName = "albumName"
        val albumID = "1"
        // When
        viewModel.getPhotos()
        // Then
        viewModel.state.test {
            assertEquals(
                AlbumDetailsUiState(
                    isLoading = true,
                    isError = false,
                    albumName = albumName,
                    albumID = albumID.toInt(),
                    error = null
                ), awaitItem()
            )

        }
    }

    @Test
    fun `getPhotos() when successful call, then should return list of photos`() = runTest {
        // Given
        val photos = listOf(
            Photo(
                id = 1,
                albumId = 1,
                title = "title",
                url = "url",
                thumbnailUrl = "thumbnailUrl"
            )
        )
        coEvery { getPhotosUseCase(1) } returns photos
        // When
        viewModel.getPhotos()
        delay(10L)
        // Then
        viewModel.state.test {
            assertEquals(
                AlbumDetailsUiState(
                    isLoading = false,
                    isError = false,
                    albumName = "albumName",
                    albumID = 1,
                    photos = photos.map { it.toUiModel() },
                    filteredPhotos = photos.map { it.toUiModel() },
                    error = null
                ), awaitItem()
            )
        }
    }

    @Test
    fun `getPhotos() when unsuccessful call, then should return error`() = runTest {
        // Given
        val error = "error"
        // When
        coEvery { getPhotosUseCase(1) } throws NetworkErrorException(error)
        viewModel.getPhotos()
        delay(10L)
        // Then
        viewModel.state.test {
            val actual = awaitItem()
            assertEquals(
                AlbumDetailsUiState(
                    isLoading = false,
                    isError = true,
                    albumName = "albumName",
                    albumID = 1,
                    error = ErrorHandler.NetworkError(error),
                ), actual
            )
        }
    }

    @Test
    fun `getPhotos() when successful call with empty list, then should return empty list of photos`() =
        runTest {
            // Given
            val photos = emptyList<Photo>()
            coEvery { getPhotosUseCase(1) } throws EmptyResponseException()
            // When
            viewModel.getPhotos()
            delay(10L)
            // Then
            viewModel.state.test {
                assertEquals(
                    AlbumDetailsUiState(
                        isLoading = false,
                        isError = true,
                        albumName = "albumName",
                        albumID = 1,
                        error = ErrorHandler.EmptyResponse("Op's..! No Content Found"),
                    ), awaitItem()
                )
            }
        }

    @Test
    fun `onSearchTextChanged() when search query is empty, then should return list of photos`() =
        runTest {
            // Given
            val photos = listOf(
                Photo(
                    id = 1,
                    albumId = 1,
                    title = "title",
                    url = "url",
                    thumbnailUrl = "thumbnailUrl"
                )
            )
            coEvery { getPhotosUseCase(1) } returns photos
            viewModel.getPhotos()
            delay(10L)
            // When
            viewModel.onSearchTextChanged("")
            // Then
            viewModel.state.test {
                assertEquals(
                    AlbumDetailsUiState(
                        isLoading = false,
                        isError = false,
                        albumName = "albumName",
                        albumID = 1,
                        searchQuery = "",
                        photos = photos.map { it.toUiModel() },
                        filteredPhotos = photos.map { it.toUiModel() },
                        error = null
                    ), awaitItem()
                )
            }
        }

    @Test
    fun `onSearchTextChanged() when search query is not empty, then should return filtered list of photos`() =
        runTest {
            // Given
            val photos = listOf(
                Photo(
                    id = 1,
                    albumId = 1,
                    title = "title",
                    url = "url",
                    thumbnailUrl = "thumbnailUrl"
                ),
                Photo(
                    id = 2,
                    albumId = 1,
                    title = "description",
                    url = "url2",
                    thumbnailUrl = "thumbnailUrl2"
                )
            )
            val filteredPhotos = listOf(
                Photo(
                    id = 1,
                    albumId = 1,
                    title = "title",
                    url = "url",
                    thumbnailUrl = "thumbnailUrl"
                )
            )
            coEvery { getPhotosUseCase(1) } returns photos
            viewModel.getPhotos()
            delay(10L)
            // When
            viewModel.onSearchTextChanged("title")
            // Then
            viewModel.state.test {
                assertEquals(
                    AlbumDetailsUiState(
                        isLoading = false,
                        isError = false,
                        albumName = "albumName",
                        albumID = 1,
                        searchQuery = "title",
                        photos = photos.map { it.toUiModel() },
                        filteredPhotos = filteredPhotos.map { it.toUiModel() },
                        error = null
                    ), awaitItem()
                )
            }
        }

    @Test
    fun `onPhotoClicked() when successful call, then should return success state`() = runTest {
        // Given
        val imgUrl = "imgUrl"
        // When
        viewModel.onPhotoClicked(imgUrl)
        // Then
        viewModel.effect.test {
            assertEquals(
                AlbumDetailsUiEffect.NavigateToPhoto(imgUrl), awaitItem()
            )
        }
    }
}