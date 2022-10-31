package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, rememberMe: Boolean, result: (UiState<String>) -> Unit)
    fun saveRememberMePref(id: String, result: (UiState<String>) -> Unit)
    fun logout()
}