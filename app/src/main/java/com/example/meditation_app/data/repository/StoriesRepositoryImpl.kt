package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.utils.Resource
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : StoriesRepository {

    override suspend fun getAllStories(result: (Resource<List<Stories>>) -> Unit) = firebaseDataSource.getAllStories(result)

}