package com.example.hostel1.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hostel1.Integration.LoggingInterceptor
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val Registerbtn = findViewById<Button>(R.id.regButton)
        val studentname = findViewById<EditText>(R.id.hostelName)
        val libid = findViewById<EditText>(R.id.username)
        val fname = findViewById<EditText>(R.id.password)
        val pass = findViewById<EditText>(R.id.confirmPassword)
        val email = findViewById<EditText>(R.id.emailId)
        val room_no = findViewById<EditText>(R.id.room)
        val contact = findViewById<EditText>(R.id.contact)

        Registerbtn.setOnClickListener {

            val intent = Intent(this@Registration, Login::class.java)
            startActivity(intent)
            finish()

//            val data = mapOf("username" to libid, "password" to paas, "email" to email, "stname" to studentname, f_name )

//            val data = mapOf("name" to studentname,  "username" to  libid, "password" to pass,
//                "email" to email, "f_name" to fname, "contact" to contact, "room_no" to room_no)

//            makePostRegister(data)
//            println(data)

        }
    }

        private fun makePostRegister(data: Map<String, EditText>) {
            val call = RetrofitClient.apiService.makePostRegister(data)
            call.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        println("POST request successful : ${response.headers()}")
                        val client = OkHttpClient.Builder()
                            .addInterceptor(LoggingInterceptor())
                            .build()
                    val intent = Intent(this@Registration, Login::class.java)
                    startActivity(intent)
                    finish()
                    } else {
                        println("POST request failed: ${response.code()}")
                        println("POST request failed: $response")
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    println("POST request failed: $t")
                }
            })

        }
}