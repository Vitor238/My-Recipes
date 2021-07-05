package com.vitor238.myrecipes.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vitor238.myrecipes.R
import com.vitor238.myrecipes.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = if (FirebaseAuth.getInstance().currentUser != null) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, WelcomeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }
}