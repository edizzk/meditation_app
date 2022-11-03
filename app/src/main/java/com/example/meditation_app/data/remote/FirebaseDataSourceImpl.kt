package com.example.meditation_app.data.remote

import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.FireStoreCollection
import com.example.meditation_app.utils.Resource
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
        user: User, result: (Resource<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    user.id = it.result.user?.uid ?: ""
                    addUser(user) { state ->
                        when(state){
                            is Resource.Success -> {result.invoke(Resource.Success("User registered successfully!"))}
                            is Resource.Failure -> {result.invoke(Resource.Failure(state.error))}
                        }
                    }
                }else {
                    try {
                        throw it.exception ?: Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(Resource.Failure("Authentication failed, password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(Resource.Failure("Authentication failed, invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(Resource.Failure("Authentication failed, email already registered"))
                    } catch (e: Exception) {
                        result.invoke(Resource.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {result.invoke(Resource.Failure(it.localizedMessage))}
    }

    override fun loginUser(
        email: String,
        password: String,
        result: (Resource<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(Resource.Success(task.result.user?.uid ?: ""))
                }
            }
            .addOnFailureListener {result.invoke(Resource.Failure("Authentication failed, Check email and password"))}
    }

    override fun addUser(user: User, result: (Resource<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id!!)
        document
            .set(user)
            .addOnSuccessListener{result.invoke(Resource.Success("User has been update successfuly"))}
            .addOnFailureListener{result.invoke(Resource.Failure(it.localizedMessage))}
    }

    override fun getCurrentUser(result: (Resource<User?>) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null)
            database.collection(FireStoreCollection.USER).document(userId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = it.result.toObject<User>()
                        result.invoke(Resource.Success(user))
                    }else{
                        result.invoke(Resource.Failure(it.exception.toString()))
                    }
                }
                .addOnFailureListener{result.invoke(Resource.Failure(it.message))}
        else result.invoke(Resource.Failure("userId is null"))

    }

    override fun logout() {
        auth.signOut()
    }

    override fun getAllMeditations(result: (Resource<List<Meditations>>) -> Unit) {
        database.collection(FireStoreCollection.MED)
            .get()
            .addOnSuccessListener{
                val medList = arrayListOf<Meditations>()
                for (doc in it) {
                    val med = doc.toObject(Meditations::class.java)
                    medList.add(med)
                }
                result.invoke(Resource.Success(medList))
            }
            .addOnFailureListener { result.invoke(Resource.Failure(it.localizedMessage)) }
    }

    override fun getAllStories(result: (Resource<List<Stories>>) -> Unit) {
        database.collection(FireStoreCollection.STORY)
            .get()
            .addOnSuccessListener{
                val storyList = arrayListOf<Stories>()
                for (doc in it) {
                    val story = doc.toObject(Stories::class.java)
                    storyList.add(story)
                }
                result.invoke(Resource.Success(storyList))
            }
            .addOnFailureListener { result.invoke(Resource.Failure(it.localizedMessage)) }
    }
}