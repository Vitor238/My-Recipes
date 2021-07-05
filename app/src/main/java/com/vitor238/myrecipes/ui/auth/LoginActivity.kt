package com.vitor238.myrecipes.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.databinding.ActivityLoginBinding
import com.vitor238.myrecipes.ui.base.BaseActivity
import com.vitor238.myrecipes.ui.home.HomeActivity
import com.vitor238.myrecipes.ui.viewmodel.AuthViewModel
import com.vitor238.myrecipes.utils.text

class LoginActivity : BaseActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupViewModels()
    }

    private fun setupViewModels() {

        val authViewModelFactory = AuthViewModel.AuthViewModelFactory(application)
        authViewModel = ViewModelProvider(this, authViewModelFactory)
            .get(AuthViewModel::class.java)

        authViewModel.authenticated.observe(this) { authenticated ->
            if (authenticated) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        authViewModel.errorMessage.observe(this) {
            showSnackBar(binding.root, it)
        }
    }

    private fun setupViews() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.textInputEmail.text()
            val password = binding.textInputPassword.text()
            if (email.isNotBlank()) {
                if (password.isNotBlank()) {
                    authViewModel.login(email, password)
                } else {
                    showSnackBar(binding.buttonLogin, R.string.type_your_password)
                }
            } else {
                showSnackBar(binding.buttonLogin, R.string.type_your_email)
            }
        }
    }

    companion object {
        val TAG = LoginActivity::class.simpleName
    }
}