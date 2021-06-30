package com.vitor238.myrecipes.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.text(): String {
    return if (this.editText?.text.isNullOrBlank()) {
        ""
    } else {
        this.editText?.text.toString()
    }
}