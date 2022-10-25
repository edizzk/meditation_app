package com.example.meditation_app.di

import android.content.SharedPreferences
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.data.repository.AuthRepositoryImpl
import com.google.gson.Gson
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
        dataSource: FirebaseDataSource,
        prefs: SharedPreferences,
        gson: Gson
    ): AuthRepository = AuthRepositoryImpl(dataSource, prefs, gson)

}