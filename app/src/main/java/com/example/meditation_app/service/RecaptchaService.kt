package com.example.meditation_app.service

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.secretKey
import com.example.meditation_app.utils.siteKey
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import org.json.JSONObject

class RecaptchaService {

    fun captcha(activity: FragmentActivity, queue: RequestQueue, result: (UiState<String>) -> Unit) {
        SafetyNet.getClient(activity).verifyWithRecaptcha(siteKey)
            .addOnSuccessListener(activity) { response ->
                if (response.tokenResult?.isNotEmpty() == true) {
                    handleCaptchaResult(response.tokenResult, queue) {
                        when(it){
                            is UiState.Success -> result.invoke(UiState.Success(it.data))
                            is UiState.Failure -> result.invoke(UiState.Failure(it.error))
                        }
                    }
                }
            }
            .addOnFailureListener(activity) { e ->
                if (e is ApiException) {
                    result.invoke(UiState.Failure(CommonStatusCodes.getStatusCodeString(e.statusCode)))
                    Log.d(TAG, "Failure: " + CommonStatusCodes.getStatusCodeString(e.statusCode))
                } else {
                    result.invoke(UiState.Failure(e.message.toString()))
                    Log.d(TAG, "Failure: " + e.message.toString())
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
                        Log.d(TAG, "Success")
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

    companion object {
        private const val TAG = "RecaptchaService: "
    }

}