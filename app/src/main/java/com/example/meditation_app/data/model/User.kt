package com.example.meditation_app.data.model

data class User(
    var id: String? = "",
    val first_name: String? = "",
    val last_name: String? = "",
    val email: String? = "",
    var vip: Boolean? = false
)