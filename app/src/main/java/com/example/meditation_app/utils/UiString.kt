package com.example.meditation_app.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiString {
    data class DynamicString(val value: String): UiString()
    class StringResources(@StringRes val resId: Int, vararg val args: Any): UiString()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResources -> context.getString(resId, *args)
        }
    }
}