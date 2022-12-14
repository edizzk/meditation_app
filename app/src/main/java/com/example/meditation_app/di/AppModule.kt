package com.example.meditation_app.di

import android.content.Context
import android.content.SharedPreferences
import com.example.meditation_app.service.MediaPlayerService
import com.example.meditation_app.service.RecaptchaService
import com.example.meditation_app.utils.SharedPrefConstants.LOCAL_SHARED_PREF
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(LOCAL_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideRecaptchaService(): RecaptchaService = RecaptchaService()

    @Provides
    @Singleton
    fun provideMediaPlayerService(): MediaPlayerService = MediaPlayerService()
}