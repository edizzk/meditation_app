package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.utils.UiState
import javax.inject.Inject

class MeditationsRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : MeditationsRepository {

    override suspend fun getAllMeditations(result: (UiState<List<Meditations>>) -> Unit) = firebaseDataSource.getAllMeditations(result)

}