package com.example.hostel1.Integration

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(this)
    }
}