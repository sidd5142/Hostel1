package com.example.hostel1.Complaint.RegisterComp.Status

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel1.Integration.Complain
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class status : AppCompatActivity() {

    private lateinit var recyclerViewStatus: RecyclerView
    private lateinit var complaintListAdapter: ComplaintListAdapter
    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        recyclerViewStatus = findViewById(R.id.recyclerViewStatus)
        recyclerViewStatus.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.progressBar)

        acceptComplaint()

        progressBar.visibility = View.VISIBLE
    }

    private fun acceptComplaint() {
        try {
            val call: Call<List<Complain>> = RetrofitClient.apiService.getComplaint()

            call.enqueue(object : Callback<List<Complain>> {
                override fun onResponse(call: Call<List<Complain>>, response: Response<List<Complain>>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        response.body()?.let { complaints ->
                            Log.d("Main", "success! $complaints")
                            complaintListAdapter = ComplaintListAdapter(complaints)
                            recyclerViewStatus.adapter = complaintListAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<List<Complain>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.e("Main", "Failed mate ${t.message}")
                }
            })
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Log.e("Main", "Exception: ${e.message}")
        }
    }
}
