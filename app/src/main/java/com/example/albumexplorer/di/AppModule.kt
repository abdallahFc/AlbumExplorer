package com.example.albumexplorer.di

import com.example.albumexplorer.data.remote.AlbumService
import com.example.albumexplorer.ui.util.DispatcherProvider
import com.example.albumexplorer.ui.util.StandardDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }

    @Provides
    @Singleton
    fun provideStandardProvider(): DispatcherProvider {
        return StandardDispatcher()
    }
}