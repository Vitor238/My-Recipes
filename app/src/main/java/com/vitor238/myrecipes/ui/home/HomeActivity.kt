package com.vitor238.myrecipes.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.data.model.Category
import com.vitor238.myrecipes.databinding.ActivityHomeBinding
import com.vitor238.myrecipes.ui.WelcomeActivity
import com.vitor238.myrecipes.ui.base.BaseActivity
import com.vitor238.myrecipes.ui.viewmodel.AuthViewModel
import com.vitor238.myrecipes.utils.Categories.CAKES_AND_PIES
import com.vitor238.myrecipes.utils.Categories.DESSERTS
import com.vitor238.myrecipes.utils.Categories.DRINKS
import com.vitor238.myrecipes.utils.Categories.FISH_AND_SEAFOOD
import com.vitor238.myrecipes.utils.Categories.LATEST
import com.vitor238.myrecipes.utils.Categories.MEAT
import com.vitor238.myrecipes.utils.Categories.OTHER
import com.vitor238.myrecipes.utils.Categories.PASTA
import com.vitor238.myrecipes.utils.Categories.SALADS_AND_SAUCES
import com.vitor238.myrecipes.utils.Categories.SNACKS
import com.vitor238.myrecipes.utils.Categories.SOUPS

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

        val list = listOf(
            Category(
                LATEST,
                R.string.latest_saved,
                R.drawable.shania_pinnata_unsplash
            ),
            Category(
                DRINKS,
                R.string.drinks,
                R.drawable.drinks
            ),
            Category(
                PASTA,
                R.string.pasta,
                R.drawable.pasta
            ),
            Category(
                CAKES_AND_PIES,
                R.string.cakes_and_pies,
                R.drawable.cakes_and_pies
            ),
            Category(
                DESSERTS,
                R.string.desserts,
                R.drawable.desserts
            ),
            Category(
                SALADS_AND_SAUCES,
                R.string.salads_and_sauces,
                R.drawable.salads_and_sauces
            ),
            Category(
                MEAT,
                R.string.meats,
                R.drawable.meats
            ),
            Category(
                FISH_AND_SEAFOOD,
                R.string.fish_and_seafood,
                R.drawable.fish_and_seafood
            ),
            Category(
                SOUPS,
                R.string.soups,
                R.drawable.soups_and_sauces
            ),

            Category(
                SNACKS,
                R.string.snacks,
                R.drawable.snacks
            ),
            Category(
                OTHER,
                R.string.other,
                R.drawable.other
            )
        )

        binding.recyclerView.setHasFixedSize(true)
        val adapter = CategoriesAdapter {}
        binding.recyclerView.adapter = adapter
        adapter.submitList(list)


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