package com.example.meditation_app.di

import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.data.remote.FirebaseDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        auth: FirebaseAuth,
        database: FirebaseFirestore
    ): FirebaseDataSource = FirebaseDataSourceImpl(auth, database)

}