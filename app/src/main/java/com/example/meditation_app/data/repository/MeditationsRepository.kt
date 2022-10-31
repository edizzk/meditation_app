package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.utils.UiState

interface MeditationsRepository {
    suspend fun getAllMeditations(result: (UiState<List<Meditations>>) -> Unit)
}