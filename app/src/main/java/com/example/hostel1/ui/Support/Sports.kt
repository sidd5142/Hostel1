package com.example.hostel1.ui.Support

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hostel1.R

class Sports : AppCompatActivity() {

    data class Sports(val sno: String, val name: String, val hostelName: String, val year: String, val mobileNumber: String)

    private val sports = listOf(
        Sports("1", "Mr. Nagesh Tiwari", "chandragupta", "First year", "8588816603"),
        Sports("2", "Mr. Nirmal Kumar", "CV Raman", "Second year", "788978655"),
        Sports("3", "Mr. Muneesh Kumar", "Tagore", "Third year", "8588816609")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sports)

        val boysTableLayout: TableLayout = findViewById(R.id.boysTableLayout)
        populateTable(boysTableLayout, sports)
    }

    private fun populateTable(tableLayout: TableLayout, wardens: List<Sports>) {
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
