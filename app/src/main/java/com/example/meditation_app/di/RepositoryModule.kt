package com.example.meditation_app.di

import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.data.repository.AuthRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRepositoryImp: AuthRepositoryImp
    ): AuthRepository = authRepositoryImp

}