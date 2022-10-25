package com.example.meditation_app.view.auth.register

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.meditation_app.R
import com.example.meditation_app.databinding.FragmentRegisterBinding
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.utils.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(email: String, password: String, user: User) {
        repository.registerUser(email, password, user) { _register.value = it }
    }

    fun captcha(activity: FragmentActivity, queue: RequestQueue, result: (UiState<String>) -> Unit) {
        SafetyNet.getClient(activity).verifyWithRecaptcha(siteKey)
            .addOnSuccessListener(activity) { response ->
                if (response.tokenResult?.isNotEmpty() == true) {
                    handleCaptchaResult(response.tokenResult, queue) {
                        when(it){
                            is UiState.Success -> {
                                result.invoke(UiState.Success(it.data))
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(it.error))
                            }
                        }
                    }
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
    private fun handleCaptchaResult(responseToken: String?, queue: RequestQueue, result: (UiState<String>) -> Unit) {
        val url = "https://www.google.com/recaptcha/api/siteverify"
        val request: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("success")) {
                        result.invoke(UiState.Success("Success"))
                    }
                } catch (ex: Exception) {
                    Log.d(TAG, "Error message: " + ex.message)
                    result.invoke(UiState.Failure(ex.message))
                }
            },
            Response.ErrorListener { error ->
                Log.d(TAG, "Error message: " + error.message)
                result.invoke(UiState.Failure(error.message))
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["secret"] = secretKey
                params["response"] = responseToken!!
                return params
            }
        }
        request.retryPolicy = DefaultRetryPolicy(
            50000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
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

    companion object {
        private const val TAG = "RegisterViewModel: "
    }
}