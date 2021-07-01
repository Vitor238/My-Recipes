package com.vitor238.myrecipes.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.databinding.ActivityHomeBinding
import com.vitor238.myrecipes.ui.base.BaseActivity
import com.vitor238.myrecipes.ui.viewmodel.AuthViewModel

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.toolbar, R.string.app_name)

        val authViewModelFactory = AuthViewModel.AuthViewModelFactory(application)
        authViewModel = ViewModelProvider(this, authViewModelFactory)
            .get(AuthViewModel::class.java)

        authViewModel.loggedOut.observe(this) { loggedOut ->
            if (loggedOut) {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_logout -> {
                authViewModel.logout()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}