package com.example.hostel1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hostel1.ui.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        // Check if savedInstanceState is null to avoid overlapping fragments
        if (savedInstanceState == null) {
            // Replace the container with HomeFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment<Any>())
                .commit()
        }
    }
}
