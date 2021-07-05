package com.vitor238.myrecipes.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val type: String,
    @StringRes val name: Int,
    @DrawableRes val thumbnail: Int
)