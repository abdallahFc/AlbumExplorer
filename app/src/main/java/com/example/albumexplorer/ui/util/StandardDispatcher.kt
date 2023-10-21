package com.example.albumexplorer.ui.util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//using the standard dispatcher for testing
class StandardDispatcher: DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

}