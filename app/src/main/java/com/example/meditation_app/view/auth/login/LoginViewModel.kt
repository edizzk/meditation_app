package com.example.meditation_app.view.auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation_app.R
import com.example.meditation_app.databinding.FragmentLoginBinding
import com.example.meditation_app.model.User
import com.example.meditation_app.repo.AuthRepository
import com.example.meditation_app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun login(email: String,password: String, rememberMe: Boolean) {
        _login.value = UiState.Loading
        repository.loginUser(email, password, rememberMe){ _login.value = it }
    }

    fun getRememberMePref(result: (User?) -> Unit) = repository.getRememberMePref(result)

    fun validation(binding: FragmentLoginBinding, context: Context, result: (UiState<String>) -> Unit) {
        if (binding.emailEditText.text.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_email).asString(context)
            ))
            return
        }else {
            if (!binding.emailEditText.text.toString().isValidEmail()){
                result.invoke(UiState.Failure(
                    UiString.StringResources(R.string.invalid_email).asString(context)
                ))
                return
            }
        }
        if (binding.passwordEditText.text.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_password).asString(context)
            ))
            return
        }
        result.invoke(UiState.Success(""))
    }
}