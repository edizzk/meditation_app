package com.example.meditation_app.view.onboarding

import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.repository.DataStoreRepository
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val repository: DataStoreRepository
): BaseViewModel() {

    fun saveOnBoardingStatePref(onBoardingState: Boolean) = repository.saveOnBoardingStatePref(onBoardingState)

}