package com.vitor238.myrecipes.utils

import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabaseUtils {
    private val baseFirebaseRef = FirebaseDatabase.getInstance().reference

    val usersRef
        get() = baseFirebaseRef.child("users")
}