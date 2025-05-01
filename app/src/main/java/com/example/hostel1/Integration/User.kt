// Corrected Complain.kt
package com.example.hostel1.Integration

data class Complain(
    val id: Int,
    val type: String,
    val problem: String,
    val created_date : String,
    val created_time : String,
    val description: String,
    val approval_status: Boolean,
    val student: Student
)

data class Student(
    val id: Int,
    val email: String,
    val std_name: String,
    val dept: String,
    val year: String,
    val u_rollno: String,
    val std_contact: String,
    val f_contact: String,
    val m_contact: String,
    val room_altd: String,
    val seater_altd: String
)
