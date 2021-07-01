package com.vitor238.myrecipes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitor238.myrecipes.data.repository.AuthRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository: AuthRepository = AuthRepository(application)
    val errorMessage = authRepository.errorMessage
    val authenticated = authRepository.authenticated
    val loggedOut = authRepository.loggedOut

    fun register(name: String, email: String, password: String) {
        authRepository.register(name, email, password)
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password)
    }

    fun logout() {
        authRepository.logout()
    }

    class AuthViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}