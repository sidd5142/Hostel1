package com.example.hostel1.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.hostel1.NavHome
import com.example.hostel1.R
import com.example.hostel1.Integration.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize RetrofitClient and SharedPreferences
        RetrofitClient.init(this)
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        val hostelNameEditText = findViewById<EditText>(R.id.hostelName)
        val emailEditText = findViewById<EditText>(R.id.emailId)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val hostelName = hostelNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            Log.e("LoginAttempt", "identifier: $email, password: $password")

            makePostRequest(email, password)
        }
    }

    private fun makePostRequest(email: String, password: String) {
        val call = RetrofitClient.apiService.makePostRequest(email, password)

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("Login", "POST request successful: ${response.headers()}")

                    val cookies = response.headers().values("Set-Cookie")
                    saveCookies(cookies)

                    Log.d("CheckCookies", "Set-Cookie: $cookies")

                    val intent = Intent(this@Login, NavHome::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Login", "POST failed: ${response.code()}, Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("Login", "POST request failed", t)
            }
        })
    }

    private fun saveCookies(cookies: List<String>) {
        val editor = sharedPreferences.edit()
        val cookieSet = HashSet(cookies)
        editor.putStringSet("cookies", cookieSet)
        editor.apply()
    }

    private fun getCookies(): List<String>? {
        val cookieSet = sharedPreferences.getStringSet("cookies", null)
        return cookieSet?.toList()
    }
}
