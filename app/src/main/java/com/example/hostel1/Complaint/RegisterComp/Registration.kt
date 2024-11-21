package com.example.hostel1.Complaint.RegisterComp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.hostel1.Complaint.RegisterComp.Status.Submission
import com.example.hostel1.Complaint.RegisterComp.Status.status
import com.example.hostel1.R
import com.example.hostel1.databinding.ActivityRegistration2Binding

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistration2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistration2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val statusButton = findViewById<Button>(R.id.statusButton)

        val images = listOf(R.drawable.feedback, R.drawable.complaints, R.drawable.council) // Replace with your image resources

        val adapter = ImagePagerAdapter(images)
        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        registerButton.setOnClickListener {
            startActivity(Intent(this, Submission::class.java))
        }

        statusButton.setOnClickListener {
            startActivity(Intent(this, status::class.java))
        }
    }
}