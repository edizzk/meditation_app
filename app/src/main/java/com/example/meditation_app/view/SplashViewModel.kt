package com.example.meditation_app.view

import androidx.lifecycle.*
import com.example.meditation_app.data.repository.DataStoreRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
): ViewModel() {

    fun getOnBoardingStatePref(result: (Boolean) -> Unit) = repository.getOnBoardingStatePref {result.invoke(it)}

}