package com.example.meditation_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meditations(
    val id: Int? = 0,
    private val name: String? = "",
    private val subtitle: String? = "",
    private val desc: String? = "",
    private val image: String? = ""
): Parcelable, DetailViewObject {

    override fun getName() = name
    override fun getSubTitle() = subtitle
    override fun getDesc() = desc
    override fun getImage() = image

}