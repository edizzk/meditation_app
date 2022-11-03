package com.example.meditation_app.data.remote

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.Resource

interface FirebaseDataSource {
    fun registerUser(email: String, password: String, user: User, result: (Resource<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (Resource<String>) -> Unit)
    fun addUser(user: User, result: (Resource<String>) -> Unit)
    fun getCurrentUser(result: (Resource<User?>) -> Unit)
    fun logout()

    fun getAllMeditations(result: (Resource<List<Meditations>>) -> Unit)

    fun getAllStories(result: (Resource<List<Stories>>) -> Unit)
}