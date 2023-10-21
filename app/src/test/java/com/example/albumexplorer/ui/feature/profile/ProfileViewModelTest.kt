package com.example.albumexplorer.ui.feature.profile


import app.cash.turbine.test
import com.example.albumexplorer.domain.util.EmptyResponseException
import com.example.albumexplorer.domain.util.NetworkErrorException
import com.example.albumexplorer.domain.util.ServerErrorException
import com.example.albumexplorer.domain.model.Address
import com.example.albumexplorer.domain.model.Album
import com.example.albumexplorer.domain.model.User
import com.example.albumexplorer.domain.usecase.GetAlbumsUseCase
import com.example.albumexplorer.domain.usecase.GetRandomUserUseCase
import com.example.albumexplorer.ui.base.ErrorHandler
import com.example.albumexplorer.ui.util.MainCoroutineRule
import com.example.albumexplorer.ui.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {
    private lateinit var getRandomUser: GetRandomUserUseCase
    private lateinit var getAlbumsUseCase: GetAlbumsUseCase
    private lateinit var viewModel: ProfileViewModel
    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var testDispatcher: TestDispatchers

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testDispatcher = TestDispatchers()
        getRandomUser = mockk(relaxed = true)
        getAlbumsUseCase = mockk(relaxed = true)
        viewModel = ProfileViewModel(getRandomUser, getAlbumsUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getUsers() when successful call, then should return success state`() =
        runTest {
            // Given
            val user = User(
                id = 1,
                username = "username",
                address = Address(
                    city = "city",
                    street = "street",
                    suite = "suite",
                    zipcode = "zipcode"
                )
            )
            coEvery { getRandomUser() } returns user
            // When
            viewModel.getUsers()
            delay(10L)
            // Then
            viewModel.state.test {
                assertEquals(
                    awaitItem(),
                    ProfileUiState(
                        isLoading = false,
                        isError = false,
                        albums = emptyList(),
                        userName = user.username,
                        city = user.address.city,
                        street = user.address.street,
                        suite = user.address.suite,
                        zipcode = user.address.zipcode,
                        error = null
                    )
                )
            }
        }

    @Test
    fun `getUsers() when unsuccessful call, then should return error`() =
        runTest {
            // Given
            val error = "error"
            coEvery { getRandomUser() } throws NetworkErrorException(error)
            // When
            viewModel.getUsers()
            delay(10L)
            // Then
            viewModel.state.test {
                assertEquals(true, awaitItem().isError)
            }
        }

    @Test
    fun `getUsers() when server error, then should return error state`() =
        runTest {
            // Given
            val error = "server error"
            coEvery { getRandomUser() } throws ServerErrorException(error)
            // When
            viewModel.getUsers()
            delay(10L)
            // Then
            viewModel.state.test {
                val actualState = awaitItem()
                assertEquals(
                    ProfileUiState(
                        userName = "",
                        city = "",
                        street = "",
                        suite = "",
                        zipcode = "",
                        albums = emptyList(),
                        isError = true,
                        error = ErrorHandler.ServerError(error)
                    ),
                    actualState
                )
            }
        }

    @Test
    fun `getUsers() when successful call with empty list, then should return empty list of repositories`() =
        runTest {
            // Given
            val error = "Op's..! No Content Found"
            coEvery { getRandomUser() } throws EmptyResponseException()
            // When
            viewModel.getUsers()
            delay(10L)
            // Then
            viewModel.state.test {
                val actualState = awaitItem()
                assertEquals(
                    ProfileUiState(
                        userName = "",
                        city = "",
                        street = "",
                        suite = "",
                        zipcode = "",
                        albums = emptyList(),
                        isError = true,
                        error = ErrorHandler.EmptyResponse(error)
                    ),
                    actualState
                )
            }
        }


    @Test
    fun `getAlbums() when successful call, then should return success state`() =
        runTest {
            // Given
            val albums = listOf(
                Album(
                    id = 1,
                    title = "title",
                )
            )
            coEvery { getAlbumsUseCase(1) } returns albums
            // When
            viewModel.getAlbums(1)
            delay(10L)
            // Then
            viewModel.state.test {
                assertEquals(
                    awaitItem(),
                    ProfileUiState(
                        isLoading = false,
                        isError = false,
                        albums = albums.map { it.toUiModel() },
                        error = null
                    )
                )
            }
        }

    @Test
    fun `getAlbums() when unsuccessful call, then should return error`() =
        runTest {
            // Given
            val error = "error"
            coEvery { getAlbumsUseCase(1) } throws NetworkErrorException(error)
            // When
            viewModel.getAlbums(1)
            delay(10L)
            // Then
            viewModel.state.test {
                assertEquals(true, awaitItem().isError)
            }
        }

    @Test
    fun `getAlbums() when successful call with empty list, then should return empty list of repositories`() =
        runTest {
            // Given
            val error = "Op's..! No Content Found"
            coEvery { getAlbumsUseCase(1) } throws EmptyResponseException()
            // When
            viewModel.getAlbums(1)
            delay(10L)
            // Then
            viewModel.state.test {
                val actualState = awaitItem()
                assertEquals(
                    ProfileUiState(
                        userName = "",
                        city = "",
                        street = "",
                        suite = "",
                        zipcode = "",
                        albums = emptyList(),
                        isError = true,
                        error = ErrorHandler.EmptyResponse(error)
                    ),
                    actualState
                )
            }
        }

    @Test
    fun `onClickAlbumItem() when successful call, then should return success state`() =
        runTest {
            // Given
            val userName = "userName"
            val id = "1"
            val expectedEffect = ProfileUiEffect.NavigateToAlbumDetails(userName, id)
            // When
            viewModel.onClickAlbumItem(userName, id)
            // Then
            viewModel.effect.test {
                assertEquals(expectedEffect, awaitItem())
            }
        }
}
