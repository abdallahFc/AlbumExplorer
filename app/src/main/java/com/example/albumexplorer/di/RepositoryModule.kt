package com.example.albumexplorer.di

import com.example.albumexplorer.data.repo.AlbumRepositoryImpl
import com.example.albumexplorer.domain.repo.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGithubRepository(repository: AlbumRepositoryImpl): AlbumRepository
}