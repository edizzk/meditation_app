package com.example.meditation_app.view.auth

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation_app.R
import com.example.meditation_app.databinding.FragmentRegisterBinding
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

    fun register(email: String, password: String, user: User) {
       _register.value = UiState.Loading
        repository.registerUser(email, password, user) { _register.value = it }
    }

    fun login(email: String,password: String, rememberMe: Boolean) {
        _login.value = UiState.Loading
        repository.loginUser(email, password, rememberMe){ _login.value = it }
    }

    fun getRememberMePref(result: (User?) -> Unit) = repository.getRememberMePref(result)

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

    fun validation(binding: FragmentRegisterBinding, context: Context, result: (UiState<String>) -> Unit) {
        if (binding.firstNameEditText.text.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_first_name).asString(context)
            ))
            return
        }
        if (binding.lastNameEditText.text.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_last_name).asString(context)
            ))
            return
        }
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
        }else{
            if (binding.passwordEditText.text.toString().length < 6){
                result.invoke(UiState.Failure(
                    UiString.StringResources(R.string.invalid_password).asString(context)
                ))
                return
            }else {
                if(!binding.passwordEditText.text.toString().hasUpperCase()){
                    result.invoke(UiState.Failure(
                        UiString.StringResources(R.string.has_not_uppercase).asString(context)
                    ))
                    return
                }else {
                    if(!binding.passwordEditText.text.toString().hasDigit()){
                        result.invoke(UiState.Failure(
                            UiString.StringResources(R.string.has_not_digit).asString(context)
                        ))
                        return
                    }
                }
            }
        }
        result.invoke(UiState.Success(""))
    }
}