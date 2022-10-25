package com.example.meditation_app.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.remote.FirebaseDataSource
import com.example.meditation_app.utils.SharedPrefConstants.USER_PREF
import com.example.meditation_app.utils.UiState
import com.google.gson.Gson
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    override fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit) {
        firebaseDataSource.registerUser(email, password, user) {
            result.invoke(it)
        }
    }

    override fun loginUser( email: String, password: String, rememberMe: Boolean, result: (UiState<String>) -> Unit) {
        firebaseDataSource.loginUser(email, password) {
            when(it) {
                is UiState.Success -> {
                    if (rememberMe){
                        saveRememberMePref(it.data){ state ->
                            when(state) {
                                is UiState.Success -> {Log.d(TAG, "Success: remember me pref is created")}
                                is UiState.Failure -> {Log.d(TAG, "Failure: ${state.error}")}
                            }
                        }
                    }
                    result.invoke(it)
                }
                is UiState.Failure -> {result.invoke(it)}
            }
        }
    }

    override fun saveRememberMePref(id: String, result: (UiState<String>) -> Unit){
        firebaseDataSource.getUser(id){ user, state ->
            if (user != null){
                appPreferences.edit().putString(USER_PREF,gson.toJson(user)).apply()
                result.invoke(state)
            }else {
                result.invoke(state)
            }
        }
    }

    override fun getRememberMePref(result: (User?) -> Unit) {
        val userStr = appPreferences.getString(USER_PREF,null)
        if (userStr == null){
            Log.d(TAG, "Failure getRememberMePref(): $userStr")
            result.invoke(null)
        }else{
            val user = gson.fromJson(userStr,User::class.java)
            result.invoke(user)
        }
    }

    override fun logout() {
        appPreferences.edit().putString(USER_PREF, null).apply()
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl: "
    }

}