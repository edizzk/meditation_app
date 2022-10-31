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

    override fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit) =
        firebaseDataSource.registerUser(email, password, user) { result.invoke(it) }

    override fun loginUser(email: String, password: String, rememberMe: Boolean, result: (UiState<String>) -> Unit) =
        firebaseDataSource.loginUser(email, password) {
            if (it is UiState.Success && rememberMe) saveRememberMePref()
            result.invoke(it)
        }

    override fun getCurrentUser(result: (UiState<User?>) -> Unit) =
        firebaseDataSource.getCurrentUser{ result.invoke(it) }

    override fun saveRememberMePref() {
        firebaseDataSource.getCurrentUser{ state ->
            when(state) {
                is UiState.Success -> {
                    appPreferences.edit().putString(USER_PREF,gson.toJson(state.data)).apply()
                    Log.d(TAG, "saveRememberMePref() Success: remember me pref is created")
                }
                is UiState.Failure -> {Log.d(TAG, "saveRememberMePref() Failure: ${state.error}")}
            }
        }
    }

    override fun logout() {
        appPreferences.edit().putString(USER_PREF, null).apply()
        firebaseDataSource.logout()
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl: "
    }

}