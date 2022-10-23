package com.example.meditation_app.repo

import com.example.meditation_app.model.User
import com.example.meditation_app.utils.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun addUser(user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, rememberMe: Boolean, result: (UiState<String>) -> Unit)
    fun saveRememberMePref(id: String, result: (UiState<String>) -> Unit)
    fun getRememberMePref(result: (User?) -> Unit)
    fun logout(result: () -> Unit)
}