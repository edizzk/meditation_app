package com.example.meditation_app.data.repository

interface DataStoreRepository {
    fun saveOnBoardingStatePref(onBoardingState: Boolean)
    fun getOnBoardingStatePref(result: (Boolean) -> Unit)
}