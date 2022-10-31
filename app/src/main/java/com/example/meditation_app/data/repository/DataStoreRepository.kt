package com.example.meditation_app.data.repository

import com.example.meditation_app.data.model.User

interface DataStoreRepository {
    fun saveOnBoardingStatePref(onBoardingState: Boolean)
    fun getOnBoardingStatePref(result: (Boolean) -> Unit)
    fun getRememberMePref(result: (User?) -> Unit)
}