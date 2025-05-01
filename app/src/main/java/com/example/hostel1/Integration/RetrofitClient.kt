//package com.example.hostel1.Integration
//
//    import android.content.Context
//    import android.content.SharedPreferences
//    import okhttp3.OkHttpClient
//    import retrofit2.Retrofit
//    import retrofit2.converter.gson.GsonConverterFactory
//
//    object RetrofitClient {
//        private const val BASE_URL = "https://eea8-2405-201-683b-e071-126b-9ece-ae78-7d15.ngrok-free.app/"
//        private lateinit var sharedPreferences: SharedPreferences
//
//        fun init(context: Context) {
//            sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
//        }
//
//
//        val apiService: API_Service by lazy {
//            Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(
//                    OkHttpClient.Builder()
//                        .addInterceptor { chain ->
//                            val original = chain.request()
//                            val requestBuilder = original.newBuilder()
//                            val cookies = getCookies()
//                            if (cookies != null) {
//                                for (cookie in cookies) {
//                                    requestBuilder.addHeader("Cookie", cookie)
//                                }
//                            }
//                            val request = requestBuilder.build()
//                            chain.proceed(request)
//                        }
//                        .addInterceptor(LoggingInterceptor())
//                        .build()
//                )
//                .build()
//                .create(API_Service::class.java)
//        }
//
//        private fun getCookies(): List<String>? {
//            if (!::sharedPreferences.isInitialized) {
//                throw UninitializedPropertyAccessException("SharedPreferences has not been initialized")
//            }
//            val cookieSet = sharedPreferences.getStringSet("cookies", null)
//            return cookieSet?.toList()
//        }
//    }
//


package com.example.hostel1.Integration

import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://eea8-2405-201-683b-e071-126b-9ece-ae78-7d15.ngrok-free.app/"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    val apiService: API_Service by lazy {
        // Create logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log full request/response
        }

        // Optional: Add cookie interceptor if needed
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                val cookies = getCookies()
                cookies?.forEach { cookie ->
                    requestBuilder.addHeader("Cookie", cookie)
                }
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(logging) // Add logging interceptor here
            .build()

        // Create Retrofit instance
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(API_Service::class.java)
    }

    private fun getCookies(): List<String>? {
        if (!::sharedPreferences.isInitialized) {
            throw UninitializedPropertyAccessException("SharedPreferences has not been initialized")
        }
        val cookieSet = sharedPreferences.getStringSet("cookies", null)
        return cookieSet?.toList()
    }
}

