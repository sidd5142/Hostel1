package com.example.hostel1.Integration

import User
import android.widget.EditText
import com.example.hostel1.Complaint.RegisterComp.Status.Complaint
//import com.example.hostel1.Database.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface API_Service {
    @Headers("Accept: application/json")
//    @GET("users/")
    @GET("dorm/users/")
    suspend fun getUsers(): Response<List<User>>

    @POST("attendence/add-grievance/")
    fun makePostComplaint(@Body data: Complaint): Call<Unit>


    @POST("dorm/login/")
    fun makePostRequest(@Body data: Map<String, String>): Call<Unit>


    @POST("dorm/users/")
    fun makePostRegister(@Body data: Map<String, EditText>): Call<Unit>

    @POST("dorm/logout/")
//    fun makePostLogOut(@Body data: Map<String, String>): Call<Unit>
    fun makePostLogOut(@Header("Cookie") sessionCookie: String?): Call<Unit>
    fun makePostLogOut(sessionCookie: List<String>?): Call<Unit>

     @POST("attendance/mark-attendance/")
     fun makePostAttendance(@Body data: AttendanceRequest): Call<Unit>

     @GET("attendance/absenties/")
     fun getAbsent(@Query("date")date: String?): Call<List<User>>

    @GET("dorm/complaints")
    fun getComplaint():Call<List<Complain>>
}