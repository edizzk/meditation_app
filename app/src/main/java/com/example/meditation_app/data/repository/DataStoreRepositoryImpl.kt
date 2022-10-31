package com.example.meditation_app.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.SharedPrefConstants
import com.google.gson.Gson
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : DataStoreRepository {

    override fun saveOnBoardingStatePref(onBoardingState: Boolean) {
        prefs.edit().putBoolean(SharedPrefConstants.ON_BOARDING_STATE, onBoardingState).apply()
    }

    override fun getOnBoardingStatePref(result: (Boolean) -> Unit) {
        val state = prefs.getBoolean(SharedPrefConstants.ON_BOARDING_STATE,true)
        Log.d(TAG, "on_boarding_state value: $state")
        result.invoke(state)
    }

    override fun getRememberMePref(result: (User?) -> Unit) {
        val userStr = prefs.getString(SharedPrefConstants.USER_PREF,null)
        if (userStr == null){
            Log.d(TAG, "Failure getRememberMePref(): $userStr")
            result.invoke(null)
        }else{
            val user = gson.fromJson(userStr, User::class.java)
            result.invoke(user)
        }
    }

    companion object {
        private const val TAG = "DataStoreRepositoryImpl: "
    }

}