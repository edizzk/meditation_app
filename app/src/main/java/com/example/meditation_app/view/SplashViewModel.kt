package com.example.meditation_app.view

import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.repository.DataStoreRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
): BaseViewModel() {

    fun getOnBoardingStatePref(result: (Boolean) -> Unit) = repository.getOnBoardingStatePref {result.invoke(it)}

}