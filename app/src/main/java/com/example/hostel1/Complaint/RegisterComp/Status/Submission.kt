package com.example.hostel1.Complaint.RegisterComp.Status

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Submission : AppCompatActivity() {

    private lateinit var spinnerType: Spinner
    private lateinit var editTextRoomNo: EditText
    private lateinit var editTextProblem: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSubmit: Button
    private val userMailId = "user@example.com" // Replace with actual mail ID retrieval method

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        spinnerType = findViewById(R.id.spinnerType)
        editTextRoomNo = findViewById(R.id.editTextRoomNo)
        editTextProblem = findViewById(R.id.editTextProblem)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val type = spinnerType.selectedItem.toString()
            val roomNo = editTextRoomNo.text.toString()
            val problem = editTextProblem.text.toString()
            val description = editTextDescription.text.toString()

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            val complaint = Complaint(date, time, type, roomNo, problem, description, userMailId)

            makePostComplaint(complaint)

//            saveComplaintToSharedPreferences(complaint)

//            val intent = Intent(this, status::class.java)
//            startActivity(intent)
        }
    }

    private fun makePostComplaint(data: Complaint) {
        val call = RetrofitClient.apiService.makePostComplaint(data)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("Login", "POST request successful : ${response.headers()}")

                    // Navigate to NavHome activity
                    val intent = Intent(this@Submission, status::class.java)
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
}