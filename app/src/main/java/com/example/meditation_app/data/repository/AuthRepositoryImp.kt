package com.example.meditation_app.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.FireStoreCollection
import com.example.meditation_app.utils.SharedPrefConstants.USER_PREF
import com.example.meditation_app.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    user.id = it.result.user?.uid ?: ""
                    addUser(user) { state ->
                        when(state){
                            is UiState.Success -> {
                                result.invoke(
                                    UiState.Success("User registered successfully!")
                                )
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }
                        }
                    }
                }else {
                    try {
                        throw it.exception ?: Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, email already registered"))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun addUser(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id!!)
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("User has been update successfuly")
                )
            }
            .addOnFailureListener{
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun loginUser(
        email: String,
        password: String,
        rememberMe: Boolean,
        result: (UiState<String>) -> Unit) {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.invoke(UiState.Success("Login successfully!"))
                        if (rememberMe){
                            saveRememberMePref(task.result.user?.uid ?: ""){ state ->
                                when(state) {
                                    is UiState.Success -> {
                                        Log.d(TAG, "Success: remember me pref is created")
                                    }
                                    is UiState.Failure -> {
                                        Log.d(TAG, "Failure: ${state.error}")
                                    }
                                }
                            }
                        }
                    }
                }.addOnFailureListener {
                    result.invoke(UiState.Failure("Authentication failed, Check email and password"))
                }
    }

    override fun saveRememberMePref(id: String, result: (UiState<String>) -> Unit){
        database.collection(FireStoreCollection.USER).document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result.toObject<User>()
                    appPreferences.edit().putString(USER_PREF,gson.toJson(user)).apply()
                    result.invoke(UiState.Success("Remember me successfully"))
                }else{
                    result.invoke(UiState.Failure(it.exception.toString()))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.toString()))
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

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString(USER_PREF, null).apply()
        result.invoke()
    }

    companion object {
        private const val TAG = "AuthRepositoryImp: "
    }

}