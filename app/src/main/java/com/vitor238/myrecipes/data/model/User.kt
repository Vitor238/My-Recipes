package com.vitor238.myrecipes.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var id: String = "",
    val name: String = "",
    val email: String = ""
)