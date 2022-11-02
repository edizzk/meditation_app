package com.example.meditation_app.view.auth.login

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val application: Application
): BaseViewModel(){

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun login(email: String,password: String, rememberMe: Boolean) =
        repository.loginUser(email, password, rememberMe){ _login.value = it }

    fun validation(email: Editable?, password: Editable?, result: (UiState<String>) -> Unit) {
        if (email.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_email).asString(application)
            ))
            return
        }else {
            if (!email.toString().isValidEmail()){
                result.invoke(UiState.Failure(
                    UiString.StringResources(R.string.invalid_email).asString(application)
                ))
                return
            }
        }
        if (password.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_password).asString(application)
            ))
            return
        }
        result.invoke(UiState.Success(""))
        Log.d(TAG, "Validation Successfully")
    }

    companion object {
        private const val TAG = "LoginViewModel: "
    }
}