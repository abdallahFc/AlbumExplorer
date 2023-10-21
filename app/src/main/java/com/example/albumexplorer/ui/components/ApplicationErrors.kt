package com.example.albumexplorer.ui.components

import androidx.compose.runtime.Composable
import com.example.albumexplorer.ui.base.ErrorHandler

@Composable
fun ApplicationErrors(isError: Boolean , error: ErrorHandler?) {
    if (isError) {
        when (error) {
            is ErrorHandler.EmptyResponse -> {
                EmptyView(state =true)
            }
            is ErrorHandler.NetworkError -> {
                ErrorView(state = true, error = error.message)
            }
            is ErrorHandler.ServerError -> {
                ErrorView(state = true, error = error.message)
            }
            is ErrorHandler.UnknownError ->{
                ErrorView(state = true, error = error.message)
            }
            else -> {}
        }
    }

}
