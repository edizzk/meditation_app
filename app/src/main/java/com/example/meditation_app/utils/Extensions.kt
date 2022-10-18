package com.example.meditation_app.utils

import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}
