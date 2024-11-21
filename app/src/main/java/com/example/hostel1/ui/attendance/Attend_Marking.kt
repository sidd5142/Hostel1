package com.example.hostel1.ui.attendance

import User
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
//import com.example.hostel1.Database.User
import com.example.hostel1.Integration.AttendanceRecord
import com.example.hostel1.Integration.AttendanceRequest
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Attend_Marking : Fragment() {

    private lateinit var tableLayout: TableLayout
    private lateinit var presentCountTextView: TextView
    private lateinit var selectAllCheckBox: CheckBox
    private val checkBoxList = mutableListOf<CheckBox>()
    private lateinit var progressBar: ProgressBar

//    private lateinit var selectedDates: String
    private var presentCount = 0
    private var totalStudents = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            selectedDates = it.getString("date", "")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attend__marking, container, false)

        tableLayout = view.findViewById(R.id.tableLayout)
        presentCountTextView = view.findViewById(R.id.tvPresentCount)
        selectAllCheckBox = view.findViewById(R.id.selectAllCheckBox)
        progressBar = view.findViewById(R.id.progressBar)


        // Display selected date in TextView
//        view.findViewById<TextView>(R.id.tvSelectedDate).apply {
//            text = "Selected Date: $selectedDates"
//            setTextColor(resources.getColor(R.color.white))
//        }

        // Show progress bar
        progressBar.visibility = View.VISIBLE

        // Fetch users from the server
        fetchUsersFromServer()

        selectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->
            checkBoxList.forEach { it.isChecked = isChecked }
        }

        view.findViewById<Button>(R.id.submitButton)?.setOnClickListener {
            showAttendanceSummary()
        }

        return view
    }

    private fun fetchUsersFromServer() {
        lifecycleScope.launch {
            try {
                val response: Response<List<User>> =
                    withContext(Dispatchers.IO) {
                        RetrofitClient.apiService.getUsers()
                    }

                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        totalStudents = users.size
                        populateTable(users)
                    }
                } else {
                    Log.e("APIError", "Error fetching users. Code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("APIError", "Error fetching users: ${e.message}")
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun populateTable(users: List<User>?) {
        val tableLayoutData = view?.findViewById<TableLayout>(R.id.tableLayoutData)
        tableLayoutData?.let {
            it.removeAllViews() // Remove existing views if any

            checkBoxList.clear()

            users?.forEachIndexed { index, userData ->
                val tableRow = TableRow(context).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }

                val checkBox = CheckBox(context).apply {
                    tag = userData.id // Assuming userData.id is an Int and unique for each student
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            presentCount++
                        } else {
                            presentCount--
                        }
                        updatePresentCount()
                    }
                }

                checkBoxList.add(checkBox)

                Log.d("AttendancePopulation", "Adding row for student: ${userData.std_name}, ID: ${userData.id}")


                // Serial number
                val serialNumber = TextView(context).apply {
                    text = (index + 1).toString()
                    setTextColor(resources.getColor(R.color.white))
                    setPadding(8, 8, 8, 8)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }

                // Room number
                val roomNo = TextView(context).apply {
                    text = userData.room_altd
                    setTextColor(resources.getColor(R.color.white))
                    setPadding(8, 8, 8, 8)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }

                // Student's name
                val studentName = TextView(context).apply {
                    text = userData.std_name
                    setTextColor(resources.getColor(R.color.white))
                    setPadding(8, 8, 8, 8)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }

                // Father's name
                val fatherName = TextView(context).apply {
                    text = userData.f_contact
                    setTextColor(resources.getColor(R.color.white))
                    setPadding(8, 8, 8, 8)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }

                // Mobile number
                val mobileNo = TextView(context).apply {
                    text = userData.std_contact
                    setTextColor(resources.getColor(R.color.white))
                    setPadding(8, 8, 8, 8)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }

                // Add all views to the row
                tableRow.addView(checkBox)
                tableRow.addView(serialNumber)
                tableRow.addView(roomNo)
                tableRow.addView(studentName)
                tableRow.addView(fatherName)
                tableRow.addView(mobileNo)

                // Add the row to the table layout
                it.addView(tableRow)
            }
        }
    }

    private fun updatePresentCount() {
        presentCountTextView.text = "Present Count: $presentCount"
    }

    private fun showAttendanceSummary() {
        val absentCount = totalStudents - presentCount
        val message = "Present: $presentCount\nAbsent: $absentCount\n Total: $totalStudents"

        logDataAsJson()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Attendance Summary")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            sendAbsentDetails(logDataAsJson())
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun sendAbsentDetails(data1: AttendanceRequest) {
        val call = RetrofitClient.apiService.makePostAttendance(data1)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("Attend", "Attendance Sent Successfully : ${response.message()}")
                    startActivity(Intent(requireContext(), Absent_list::class.java))
                } else {
                    Log.e("AttendFault", "Failed to send attendance. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("AttendFault", "POST request failed", t)
            }
        })
    }

//    private fun logDataAsJson(): AttendanceRequest {
////        var formattedDate = selectedDates
//
//        try {
//            val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
////            val parsedDate: Date? = inputDateFormat.parse(selectedDates)
//
////            if (parsedDate != null) {
////                val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
////                formattedDate = outputDateFormat.format(parsedDate)
////            }
//        } catch (e: Exception) {
//            Log.e("AttendanceData", "Error parsing or formatting date: ${e.message}")
//        }
//
//        val attendanceData = mutableListOf<AttendanceRecord>()
//
//        for (i in 1 until tableLayout.childCount) {
//            val row = tableLayout.getChildAt(i) as TableRow
//            val checkBox = row.getChildAt(0) as CheckBox
//            val studentId = checkBox.tag as Int
//
//            val isPresent = checkBox.isChecked
//            val atdStatus = if (isPresent) "P" else "A"
//
//            val studentData = AttendanceRecord(
//                student = studentId,
//                atd_status = atdStatus
//            )
//
//            attendanceData.add(studentData)
//        }
//
//        return AttendanceRequest(
//            date = "2024-09-06",
//            atd_rec = attendanceData
//        )
//    }

    private fun logDataAsJson(): AttendanceRequest {
        val attendanceData = mutableListOf<AttendanceRecord>()

        // Iterate through the table rows and capture the attendance information
        for (i in 1 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as? TableRow ?: continue
            val checkBox = row.getChildAt(0) as? CheckBox ?: continue
            val studentId = checkBox.tag as? Int ?: continue  // Get student ID from tag

            val isPresent = checkBox.isChecked
            val atdStatus = if (isPresent) "P" else "A"

            // Add attendance record for the student
            val studentData = AttendanceRecord(
                student = studentId,
                atd_status = atdStatus
            )
            Log.e("StudentData", studentData.toString())
            attendanceData.add(studentData)
        }

        // Ensure that attendanceData is not empty
        if (attendanceData.isEmpty()) {
            Log.e("AttendanceData", "No attendance data captured")
        }

        return AttendanceRequest(
            date = "2024-09-01",  // Adjust date as per requirement
            atd_rec = attendanceData
        ).also {
            Log.d("AttendanceData", "Sending data: $it")
        }
    }

}
