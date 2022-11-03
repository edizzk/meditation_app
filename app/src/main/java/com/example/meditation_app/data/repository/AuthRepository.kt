package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.Resource

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (Resource<String>) -> Unit)
    fun loginUser(email: String, password: String, rememberMe: Boolean, result: (Resource<String>) -> Unit)
    fun getCurrentUser(result: (Resource<User?>) -> Unit)
    fun saveRememberMePref()
    fun logout()
}