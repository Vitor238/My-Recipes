package com.vitor238.myrecipes.data.repository

import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.data.model.User
import com.vitor238.myrecipes.utils.FirebaseDatabaseUtils

class AuthRepository(private val application: Application) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val defaultMessage by lazy { application.applicationContext.getString(R.string.failed_to_register) }

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private var _authenticated: MutableLiveData<Boolean> = MutableLiveData()
    val authenticated: LiveData<Boolean>
        get() = _authenticated

    private var _loggedOut: MutableLiveData<Boolean> = MutableLiveData()
    val loggedOut: LiveData<Boolean>
        get() = _loggedOut

    fun register(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                ContextCompat.getMainExecutor(application.applicationContext),
                { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        saveUserName(name, email)
                    } else {
                        _errorMessage.value = task.exception?.message
                            ?: defaultMessage

                        Log.e(TAG, task.exception?.message ?: defaultMessage)
                    }
                })
    }

    private fun saveUserName(name: String, email: String) {
        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        firebaseAuth.currentUser?.updateProfile(profile)
            ?.addOnSuccessListener {
                saveOnDatabase(name, email)
            }?.addOnFailureListener {
                _errorMessage.value = it.message
                    ?: defaultMessage
                Log.e(TAG, it.message ?: defaultMessage)
            }
    }

    private fun saveOnDatabase(name: String, email: String) {
        val id = FirebaseDatabaseUtils.usersRef.push().key
        if (id != null) {
            val user = User(
                id = id,
                name = name,
                email = email
            )
            FirebaseDatabaseUtils.usersRef.child(id).setValue(user)
                .addOnSuccessListener {
                    _authenticated.value = true
                }.addOnFailureListener {
                    _errorMessage.value = it.message ?: defaultMessage
                    Log.e(TAG, it.message ?: defaultMessage)
                }
        } else {
            _errorMessage.value = defaultMessage
            Log.e(TAG, defaultMessage)
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                ContextCompat.getMainExecutor(application.applicationContext),
                { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        _authenticated.value = true
                    } else {
                        _errorMessage.value = task.exception?.message ?: defaultMessage
                        Log.e(TAG, defaultMessage)
                    }
                })
    }

    fun logout() {
        firebaseAuth.signOut()
        _loggedOut.value = true
    }

    companion object {
        private val TAG = AuthRepository::class.simpleName
    }

}