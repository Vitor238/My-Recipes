package com.vitor238.myrecipes.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.databinding.ActivitySignUpBinding
import com.vitor238.myrecipes.ui.base.BaseActivity
import com.vitor238.myrecipes.ui.viewmodel.AuthViewModel
import com.vitor238.myrecipes.utils.text

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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
        binding.buttonSignUp.setOnClickListener {
            val name = binding.textInputName.text()
            val email = binding.textInputEmail.text()
            val password = binding.textInputPassword.text()

            if (name.isNotBlank()) {
                if (email.isNotBlank()) {
                    if (password.isNotBlank()) {
                        authViewModel.register(name, email, password)
                    } else {
                        showSnackBar(binding.buttonSignUp, R.string.type_your_password)
                    }
                } else {
                    showSnackBar(binding.buttonSignUp, R.string.type_your_email)
                }
            } else {
                showSnackBar(binding.buttonSignUp, R.string.type_your_name)
            }

        }
    }
}