package com.example.meditation_app.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.meditation_app.utils.SharedPrefConstants
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : DataStoreRepository {

    override fun saveOnBoardingStatePref(onBoardingState: Boolean) {
        prefs.edit().putBoolean(SharedPrefConstants.ON_BOARDING_STATE, onBoardingState).apply()
    }

    override fun getOnBoardingStatePref(result: (Boolean) -> Unit) {
        val state = prefs.getBoolean(SharedPrefConstants.ON_BOARDING_STATE,true)
        Log.d(TAG, "on_boarding_state value: $state")
        result.invoke(state)
    }

    companion object {
        private const val TAG = "DataStoreRepositoryImpl: "
    }

}