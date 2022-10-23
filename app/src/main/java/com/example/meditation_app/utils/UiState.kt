package com.example.meditation_app.utils

sealed class UiState<out T> {
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val error: String?): UiState<Nothing>()
}