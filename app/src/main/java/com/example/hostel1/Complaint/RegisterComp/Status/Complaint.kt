package com.example.hostel1.Complaint.RegisterComp.Status

import java.io.Serializable

data class Complaint(
    val date: String,
    val time: String,
    val type: String,
    val roomNo: String,
    val problem: String,
    val description: String,
    val mailId: String
) : Serializable