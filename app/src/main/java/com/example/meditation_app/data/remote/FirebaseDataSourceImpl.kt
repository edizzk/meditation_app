package com.example.meditation_app.data.remote

import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.FireStoreCollection
import com.example.meditation_app.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
): FirebaseDataSource {

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
                            is UiState.Success -> {result.invoke(UiState.Success("User registered successfully!"))}
                            is UiState.Failure -> {result.invoke(UiState.Failure(state.error))}
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
            .addOnFailureListener {result.invoke(UiState.Failure(it.localizedMessage))}
    }

    override fun loginUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success(task.result.user?.uid ?: ""))
                }
            }
            .addOnFailureListener {result.invoke(UiState.Failure("Authentication failed, Check email and password"))}
    }

    override fun addUser(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id!!)
        document
            .set(user)
            .addOnSuccessListener{result.invoke(UiState.Success("User has been update successfuly"))}
            .addOnFailureListener{result.invoke(UiState.Failure(it.localizedMessage))}
    }

    override fun getUser(userId: String, result: (User?, UiState<String>) -> Unit) {
        database.collection(FireStoreCollection.USER).document(userId)
        .get()
        .addOnCompleteListener {
            if (it.isSuccessful){
                val user = it.result.toObject<User>()
                result.invoke(user, UiState.Success("Remember me successfully"))
            }else{
                result.invoke(null, UiState.Failure(it.exception.toString()))
            }
        }
        .addOnFailureListener{result.invoke(null, UiState.Failure(it.message))}
    }

    override fun logout() {
        auth.signOut()
    }
}