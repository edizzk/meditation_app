package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.utils.Resource

interface MeditationsRepository {
    suspend fun getAllMeditations(result: (Resource<List<Meditations>>) -> Unit)
}