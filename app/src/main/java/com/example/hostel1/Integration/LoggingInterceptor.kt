package com.example.hostel1.Integration

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d("OkHttp", "Request Headers: $request")
        Log.d("OkHttp", "Response Headers: $response")

        return response
    }
}