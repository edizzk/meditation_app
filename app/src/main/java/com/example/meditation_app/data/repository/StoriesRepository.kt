package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.utils.UiState

interface StoriesRepository {
    suspend fun getAllStories(result: (UiState<List<Stories>>) -> Unit)
}