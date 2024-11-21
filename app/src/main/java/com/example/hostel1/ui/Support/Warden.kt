package com.example.hostel1.ui.Support

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.hostel1.R

class Warden : AppCompatActivity() {

    data class Warden(val sno: String, val name: String, val hostelName: String, val year: String, val mobileNumber: String)

    private val boysWardens = listOf(
        Warden("1", "Mr. Nagesh Tiwari", "chandragupta", "First year", "8588816603"),
        Warden("2", "Mr. Nirmal Kumar", "CV Raman", "Second year", "788978655"),
        Warden("3", "Mr. Muneesh Kumar", "Tagore", "Third year", "8588816609")
    )

    private val girlsWardens = listOf(
        Warden("1", "Ms. Uma Yadav", "Gargi", "First year", "8588816608"),
        Warden("2", "Ms. Jyoti Chauhan", "Sarojini", "Second year", "8588816607")
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warden)


        val boysTableLayout: TableLayout = findViewById(R.id.boysTableLayout)
        val girlsTableLayout: TableLayout = findViewById(R.id.girlsTableLayout)

        populateTable(boysTableLayout, boysWardens)
        populateTable(girlsTableLayout, girlsWardens)
    }

    private fun populateTable(tableLayout: TableLayout, wardens: List<Warden>) {
        for (warden in wardens) {
            val tableRow = TableRow(this)
            tableRow.addView(createTextView(warden.sno))
            tableRow.addView(createTextView(warden.name))
            tableRow.addView(createTextView(warden.hostelName))
            tableRow.addView(createTextView(warden.year))
            tableRow.addView(createCallIcon(warden.mobileNumber))
            tableLayout.addView(tableRow)
        }
    }


    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(8, 8, 8, 8)
        textView.setTextColor(resources.getColor(android.R.color.white))
        textView.setBackgroundColor(resources.getColor(android.R.color.black))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.toFloat())
        return textView
    }

    private fun createCallIcon(phoneNumber: String): ImageView {
        val callIcon = ImageView(this)
        callIcon.setImageResource(R.drawable.baseline_call_24)
        callIcon.setPadding(8, 8, 8, 8)
        callIcon.setBackgroundColor(resources.getColor(android.R.color.black))
        callIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
        return callIcon
    }
}