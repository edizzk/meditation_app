package com.example.meditation_app.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val error: String?): Resource<Nothing>()
}