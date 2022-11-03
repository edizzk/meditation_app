package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.utils.Resource

interface StoriesRepository {
    suspend fun getAllStories(result: (Resource<List<Stories>>) -> Unit)
}