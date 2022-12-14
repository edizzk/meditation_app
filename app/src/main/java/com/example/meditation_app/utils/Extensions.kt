package com.example.meditation_app.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.meditation_app.R
import java.text.SimpleDateFormat
import java.util.*


fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.hasDigit() = any(Char::isDigit)

fun String.hasUpperCase() = any(Char::isUpperCase)

fun notAvailableAlert(context: Context){
    val builder = AlertDialog.Builder(context)
    builder.setTitle(UiString.StringResources(R.string.information).asString(context))
    builder.setMessage(UiString.StringResources(R.string.not_available).asString(context))
    builder.setPositiveButton(android.R.string.ok) { _, _ ->
        Toast.makeText(context, android.R.string.ok, Toast.LENGTH_SHORT).show()
    }
    builder.show()
}

fun dateFormatter(): String {
    val currentTime: Date = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy, EE", Locale.US)
    return simpleDateFormat.format(currentTime).toString()
}