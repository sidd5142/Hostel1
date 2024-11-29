//package com.example.hostel1.register
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import com.example.hostel1.Integration.LoggingInterceptor
//import com.example.hostel1.NavHome
//import com.example.hostel1.R
//import com.example.hostel1.Integration.RetrofitClient
//import okhttp3.OkHttpClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class Login : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        val hostelNameEditText = findViewById<EditText>(R.id.hostelName)
//        val emailEditText = findViewById<EditText>(R.id.emailId)
//        val passwordEditText = findViewById<EditText>(R.id.password)
//        val loginButton = findViewById<Button>(R.id.loginButton)
//
//        loginButton.setOnClickListener {
//            val hostelName = hostelNameEditText.text.toString()
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
////            if (hostelName.isEmpty() || email.isEmpty() || password.isEmpty()) {
////                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
////                return@setOnClickListener
////            }
//            val intent = Intent(this@Login, NavHome::class.java)
//            startActivity(intent)
//            finish()
//
//            val data = mapOf<String, String>("identifier" to email, "password" to password)
//
////            val data = mapOf("identifier" to "siddharth@gmail.com", "password" to "Sidd@123")
//
////            makePostRequest(data)
//        }
//    }
//
//    private fun makePostRequest(data: Map<String, String>) {
//        val call = RetrofitClient.apiService.makePostRequest(data)
//        call.enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                if (response.isSuccessful) {
//                    println("POST request successful : ${response.headers()}")
//                    val client = OkHttpClient.Builder()
//                        .addInterceptor(LoggingInterceptor())
//                        .build()
////                    val intent = Intent(this@Registration, NavHome::class.java)
////                    startActivity(intent)
////                    finish()
//                } else {
//                    println("POST request failed: ${response.code()}")
//                    println("POST request failed: $response")
//                }
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                println("POST request failed: $t")
//            }
//        })
//    }
//}
//

//
//package com.example.hostel1.register
//
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import com.example.hostel1.NavHome
//import com.example.hostel1.R
//import com.example.hostel1.Integration.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class Login : AppCompatActivity() {
//
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        RetrofitClient.init(this)
//
//        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
//
//        val hostelNameEditText = findViewById<EditText>(R.id.hostelName)
//        val emailEditText = findViewById<EditText>(R.id.emailId)
//        val passwordEditText = findViewById<EditText>(R.id.password)
//        val loginButton = findViewById<Button>(R.id.loginButton)
//
//        loginButton.setOnClickListener {
//            val hostelName = hostelNameEditText.text.toString()
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            val data = mapOf("identifier" to email, "password" to password)
//            makePostRequest(data)
//        }
//    }
//
//    private fun makePostRequest(data: Map<String, String>) {
//        val call = RetrofitClient.apiService.makePostRequest(data)
//        call.enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                if (response.isSuccessful) {
//                    println("POST request successful : ${response.headers()}")
//                    val cookies = response.headers().values("Set-Cookie")
//                    saveCookies(cookies)
//
//                    Log.d("CHeckCookies","Set-Cookie $cookies")
//
//                    val intent = Intent(this@Login, NavHome::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    println("POST request failed: ${response.code()}")
//                    println("POST request failed: $response")
//                }
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                println("POST request failed: $t")
//            }
//        })
//    }
//
//    private fun saveCookies(cookies: List<String>) {
//        val editor = sharedPreferences.edit()
//        val cookieSet = HashSet(cookies)
//        editor.putStringSet("cookies", cookieSet)
//        editor.apply()
//    }
//
//    private fun getCookies(): List<String>? {
//        val cookieSet = sharedPreferences.getStringSet("cookies", null)
//        return cookieSet?.toList()
//    }
//}
//
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
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        val hostelNameEditText = findViewById<EditText>(R.id.hostelName)
        val emailEditText = findViewById<EditText>(R.id.emailId)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val hostelName = hostelNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val data = mapOf("identifier" to email, "password" to password)
            val intent = Intent(this@Login, NavHome::class.java)
            startActivity(intent)
            finish()
//            makePostRequest(data)
        }
    }

    private fun makePostRequest(data1: Map<String, String>) {
        val call = RetrofitClient.apiService.makePostRequest(data1)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("Login", "POST request successful : ${response.headers()}")

                    // Extract cookies from response headers
                    val cookies = response.headers().values("Set-Cookie")
                    saveCookies(cookies)

                    Log.d("CHeckCookies","Set-Cookie $cookies")

                    // Navigate to NavHome activity
                    val intent = Intent(this@Login, NavHome::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Login", "POST request failed: ${response.code()}")
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
