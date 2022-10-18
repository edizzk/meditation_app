package com.example.meditation_app.repo

import com.example.meditation_app.model.User
import com.example.meditation_app.utils.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
}