package com.example.hostel1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.hostel1.register.Login
import com.example.hostel1.register.Registration

class MainActivity : AppCompatActivity() {

//    private val SPLASH_DISPLAY_LENGTH = 3000L // Splash screen delay time in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splashGif: ImageView = findViewById(R.id.splash_gif)
        Glide.with(this).asGif().load(R.drawable.dormsplash).into(splashGif)

        Handler().postDelayed({
            if (isUserAuthenticated()) {
                // User is authenticated, redirect to HomeActivity
                val mainIntent = Intent(this@MainActivity, NavHome::class.java)
                startActivity(mainIntent)
            }
            else {
                // User is not authenticated, redirect to LoginActivity
                val mainIntent = Intent(this@MainActivity, Registration::class.java)
                startActivity(mainIntent)
            }
        }, 2000)
    }


    private fun isUserAuthenticated(): Boolean {
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val cookieSet = sharedPreferences.getStringSet("cookies", null)
        return cookieSet != null && cookieSet.isNotEmpty()
    }
}