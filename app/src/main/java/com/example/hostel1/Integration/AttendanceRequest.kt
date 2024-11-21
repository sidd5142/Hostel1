package com.example.hostel1.Integration

data class AttendanceRecord(
    val student: Int,
    val atd_status: String
)

data class AttendanceRequest(
    val date: String,
    val atd_rec: List<AttendanceRecord>
)
