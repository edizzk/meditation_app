package com.example.meditation_app.view.auth.register

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.example.meditation_app.R
import com.example.meditation_app.databinding.FragmentRegisterBinding
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.service.RecaptchaService
import com.example.meditation_app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val recaptchaService: RecaptchaService
): ViewModel(){

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(email: String, password: String, user: User) {
        repository.registerUser(email, password, user) { _register.value = it }
    }

    fun captcha(activity: FragmentActivity, queue: RequestQueue, result: (UiState<String>) -> Unit) {
        recaptchaService.captcha(activity, queue){result.invoke(it)}
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
        Log.d(TAG, "Validation Successfully")
    }

    companion object {
        private const val TAG = "RegisterViewModel: "
    }
}