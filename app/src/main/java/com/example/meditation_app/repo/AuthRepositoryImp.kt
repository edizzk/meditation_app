package com.example.meditation_app.repo

import com.example.meditation_app.model.User
import com.example.meditation_app.utils.UiState
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryImp(
    private val auth: FirebaseAuth,
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    result.invoke(
                        UiState.Success("User registered successfully!")
                    )
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

}