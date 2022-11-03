package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.utils.Resource
import javax.inject.Inject

class MeditationsRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : MeditationsRepository {

    override suspend fun getAllMeditations(result: (Resource<List<Meditations>>) -> Unit) = firebaseDataSource.getAllMeditations(result)

}