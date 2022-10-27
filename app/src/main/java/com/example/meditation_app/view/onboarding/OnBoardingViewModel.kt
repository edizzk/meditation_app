package com.example.meditation_app.view.onboarding

import androidx.lifecycle.*
import com.example.meditation_app.data.repository.DataStoreRepository
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val repository: DataStoreRepository
): ViewModel() {

    fun saveOnBoardingStatePref(onBoardingState: Boolean) = repository.saveOnBoardingStatePref(onBoardingState)

}