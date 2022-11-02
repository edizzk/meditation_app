package com.example.meditation_app.view.auth.register

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.service.RecaptchaService
import com.example.meditation_app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val recaptchaService: RecaptchaService,
    private val application: Application
): BaseViewModel(){

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(email: String, password: String, user: User) {
        repository.registerUser(email, password, user) { _register.value = it }
    }

    fun captcha(activity: FragmentActivity, queue: RequestQueue, result: (UiState<String>) -> Unit) {
        recaptchaService.captcha(activity, queue){result.invoke(it)}
    }

    fun validation(firstName: Editable?, lastName: Editable?, email: Editable?, password: Editable?, result: (UiState<String>) -> Unit) {
        if (firstName.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_first_name).asString(application)
            ))
            return
        }
        if (lastName.isNullOrEmpty()){
            result.invoke(UiState.Failure(
                UiString.StringResources(R.string.enter_last_name).asString(application)
            ))
            return
        }
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
        }else{
            if (password.toString().length < 6){
                result.invoke(UiState.Failure(
                    UiString.StringResources(R.string.invalid_password).asString(application)
                ))
                return
            }else {
                if(!password.toString().hasUpperCase()){
                    result.invoke(UiState.Failure(
                        UiString.StringResources(R.string.has_not_uppercase).asString(application)
                    ))
                    return
                }else {
                    if(!password.toString().hasDigit()){
                        result.invoke(UiState.Failure(
                            UiString.StringResources(R.string.has_not_digit).asString(application)
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