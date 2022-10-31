package com.example.meditation_app.di

import android.content.SharedPreferences
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.data.repository.*
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

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        prefs: SharedPreferences
    ): DataStoreRepository = DataStoreRepositoryImpl(prefs)

    @Provides
    @Singleton
    fun provideMeditationsRepository(
        dataSource: FirebaseDataSource
    ): MeditationsRepository = MeditationsRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideStoriesRepository(
        dataSource: FirebaseDataSource
    ): StoriesRepository = StoriesRepositoryImpl(dataSource)


}