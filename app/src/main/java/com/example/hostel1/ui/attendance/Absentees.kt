package com.example.hostel1.ui.attendance

import User
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.Integration.RetrofitClient.apiService
import com.example.hostel1.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Absentees : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var presentCountTextView: TextView
//    private lateinit var userViewModel: UserViewModel
    private lateinit var progressBar: ProgressBar

    private lateinit var selectedDates: String

    private var presentCount = 0
    private var totalStudents = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absentees)

        tableLayout = findViewById(R.id.tableLayout)
//        presentCountTextView = findViewById(R.id.tvPresentCount)
        progressBar = findViewById(R.id.progressBar) // Initialize ProgressBar

        // Retrieve selected date from intent extras
        val selectedDate = intent.getStringExtra("date")

        if (selectedDate != null) {
            selectedDates = selectedDate
        }

        // Display selected date in TextView
        findViewById<TextView>(R.id.tvSelectedDate).apply {
            text = "Selected Date: $selectedDate"
            setTextColor(resources.getColor(R.color.white))
        }

        sendRequest(selectedDate)

        // Show progress bar
        progressBar.visibility = View.VISIBLE

        findViewById<Button>(R.id.submitButton)?.setOnClickListener {
//            showAttendanceSummary()
        }
    }

    private fun sendRequest(date: String?) {
        val call: Call<List<User>> = RetrofitClient.apiService.getAbsent(date)

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    response.body()?.let {
                        Log.d("Main", "success! $it")
                        if (it.isNotEmpty()) {
                            populateTable(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Main", "Failed mate ${t.message}")
            }
        })
    }


    private fun populateTable(users: List<User>?) {
        tableLayout.removeAllViews() // Remove existing views if any
//        checkBoxList.clear()

        users?.forEachIndexed { index, userData ->
            val tableRow = TableRow(this).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
            }

//            val checkBox = CheckBox(this).apply {
//                tag = userData.id // Assuming userData.id is an Int and unique for each student
//                setOnCheckedChangeListener { _, isChecked ->
//                    if (isChecked) {
//                        presentCount++
//                    } else {
//                        presentCount--
//                    }
//                    updatePresentCount()
//                }
//            }

//            checkBoxList.add(checkBox)


            // Serial number
            val serialNumber = TextView(this).apply {
                text = (index + 1).toString()
                setTextColor(resources.getColor(R.color.white))
                setPadding(8, 8, 8, 8)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
            }

            // Room number
            val roomNo = TextView(this).apply {
                text = userData.room_altd
                setTextColor(resources.getColor(R.color.white))
                setPadding(8, 8, 8, 8)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
            }

            // Student's name
            val studentName = TextView(this).apply {
                text = userData.std_name
                setTextColor(resources.getColor(R.color.white))
                setPadding(8, 8, 8, 8)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
            }

            // Father's name
            val fatherName = TextView(this).apply {
                text = userData.f_contact
                setTextColor(resources.getColor(R.color.white))
                setPadding(8, 8, 8, 8)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
            }

            // Mobile number
            val mobileNo = TextView(this).apply {
                text = userData.std_contact
                setTextColor(resources.getColor(R.color.white))
                setPadding(8, 8, 8, 8)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
            }

            // Add all views to the row
//            tableRow.addView(checkBox)
            tableRow.addView(serialNumber)
            tableRow.addView(roomNo)
            tableRow.addView(studentName)
            tableRow.addView(fatherName)
            tableRow.addView(mobileNo)

            // Add the row to the table layout
            tableLayout.addView(tableRow)
        }
    }

//    @SuppressLint("SetTextI18n")
//    private fun updatePresentCount() {
//        presentCountTextView.text = "Present Count: $presentCount"
//    }

//    private fun showAttendanceSummary() {
//        val absentCount = totalStudents - presentCount
//        val message = "Present: $presentCount\nAbsent: $absentCount\n Total: $totalStudents"
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Attendance Summary")
//        builder.setMessage(message)
//        builder.setPositiveButton("OK") { dialog, _ ->
//            dialog.dismiss()
//        }
//        val dialog = builder.create()
//        dialog.show()
//    }
}
