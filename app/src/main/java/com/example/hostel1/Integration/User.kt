package com.example.hostel1.Integration

data class Student(
    val first_name: String,
    val last_name: String,
    val f_name: String,
    val contact: String,
    val room_no: String
)

data class UserData(
    val id: Int,
    val student: Student,
    val atd_status: String
)

data class Complain(
    val date: String,
    val time: String,
    val type: String,
    val mailId: String,
    val roomNo: String,
    val problem: String,
    val description: String
)

