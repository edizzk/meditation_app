package com.example.meditation_app.view.auth

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation_app.model.User
import com.example.meditation_app.repo.AuthRepository
import com.example.meditation_app.utils.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun register(
        email: String,
        password: String,
        user: User
    ) {
       _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }

    fun login(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.loginUser(
            email,
            password
        ){
            _login.value = it
        }
    }

    fun captcha(activity: FragmentActivity, result: (UiState<String>) -> Unit) {
        SafetyNet.getClient(activity).verifyWithRecaptcha(siteKey)
            .addOnSuccessListener(activity) { response ->
                if (response.tokenResult?.isNotEmpty() == true) {
                    result.invoke(
                        UiState.Success(response.tokenResult!!)
                    )
                }
            }
            .addOnFailureListener(activity) { e ->
                if (e is ApiException) {
                    result.invoke(
                        UiState.Failure(CommonStatusCodes.getStatusCodeString(e.statusCode))
                    )
                } else {
                    result.invoke(
                        UiState.Failure(e.message.toString())
                    )
                }
            }
    }

}