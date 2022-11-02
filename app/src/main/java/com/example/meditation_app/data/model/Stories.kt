package com.example.meditation_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stories (
    val id: Int? = 0,
    val name: String? = "",
    val subtitle: String? = "",
    val desc: String? = "",
    val image: String? = ""
): Parcelable