package com.example.hostel1.Council

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostel1.HomeActivity
import com.example.hostel1.R
import com.example.hostel1.databinding.ActivityCouncilHomeBinding
import com.example.hostel1.databinding.ActivityMainBinding
import com.example.hostel1.ui.Support.HODs
import com.example.hostel1.ui.Support.Rectors
import com.example.hostel1.ui.Support.Sports
import com.example.hostel1.ui.Support.Warden

class Council_home : AppCompatActivity() {
    private lateinit var binding : ActivityCouncilHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouncilHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton1.setOnClickListener {
            val intent = Intent(this, Warden::class.java)
            startActivity(intent)        }

        binding.imageButton2.setOnClickListener {
            val intent = Intent(this, HODs::class.java)
            startActivity(intent)        }

        binding.imageButton3.setOnClickListener {
            val intent = Intent(this, Sports::class.java)
            startActivity(intent)        }

        binding.imageButton4.setOnClickListener {
            val intent = Intent(this, Rectors::class.java)
            startActivity(intent)        }
    }
}