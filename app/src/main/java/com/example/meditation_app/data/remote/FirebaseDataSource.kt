package com.example.meditation_app.data.remote

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.UiState

interface FirebaseDataSource {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun addUser(user: User, result: (UiState<String>) -> Unit)
    fun getCurrentUser(result: (UiState<User?>) -> Unit)
    fun logout()

    fun getAllMeditations(result: (UiState<List<Meditations>>) -> Unit)

    fun getAllStories(result: (UiState<List<Stories>>) -> Unit)
}