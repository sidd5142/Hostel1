package com.example.hostel1.Mess

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hostel1.R

class MessActivity : AppCompatActivity() {

    private lateinit var menuSchedule: LinearLayout
    private lateinit var btnMonday: Button
    private lateinit var btnTuesday: Button
    private lateinit var btnWednesday: Button
    private lateinit var btnThursday: Button
    private lateinit var btnFriday: Button
    private lateinit var btnSaturday: Button
    private lateinit var btnSunday: Button

    private lateinit var tvBreakfast: TextView
    private lateinit var tvLunch: TextView
    private lateinit var tvSnacks: TextView
    private lateinit var tvDinner: TextView

    // Map to store the menu for each day
    private val menuMap: MutableMap<String, MutableMap<String, String>> = mutableMapOf(
        "Monday" to mutableMapOf(
            "Breakfast" to "Bread Butter, Jam, Corn Flakes, Boiled Eggs, Milk+Tea, Sauce",
            "Lunch" to "Rajma, Aloo Shimla Mirch, Roti, Plain Rice, Boondi Raita, Salad + Pickle",
            "Snacks" to "Samosa, Chhole + Meethi Chutney, Green Chutney, Tea",
            "Dinner" to "Aloo Jeera, Paneer Chilli, Roti, Jeera Rice"
        ),
        "Tuesday" to mutableMapOf(
            "Breakfast" to "Pancakes, Syrup, Scrambled Eggs, Orange Juice, Coffee",
            "Lunch" to "Chole, Bhature, Curd, Salad",
            "Snacks" to "Pakora, Green Chutney, Coffee",
            "Dinner" to "Butter Chicken, Naan, Jeera Rice"
        ),
        "Wednesday" to mutableMapOf(
            "Breakfast" to "Idli, Sambar, Coconut Chutney, Filter Coffee",
            "Lunch" to "Dal Makhani, Jeera Rice, Naan, Salad",
            "Snacks" to "Bhel Puri, Sev, Tea",
            "Dinner" to "Palak Paneer, Roti, Plain Rice"
        ),
        "Thursday" to mutableMapOf(
            "Breakfast" to "Poha, Sev, Jalebi, Milk, Tea",
            "Lunch" to "Kadhi, Chawal, Roti, Salad, Pickle",
            "Snacks" to "Veg Sandwich, Ketchup, Coffee",
            "Dinner" to "Chicken Curry, Roti, Rice, Salad"
        ),
        "Friday" to mutableMapOf(
            "Breakfast" to "Paratha, Curd, Pickle, Milk, Tea",
            "Lunch" to "Paneer Butter Masala, Naan, Rice, Salad",
            "Snacks" to "Vada Pav, Chutney, Tea",
            "Dinner" to "Fish Curry, Roti, Rice"
        ),
        "Saturday" to mutableMapOf(
            "Breakfast" to "Dosa, Sambar, Coconut Chutney, Coffee",
            "Lunch" to "Veg Biryani, Raita, Papad",
            "Snacks" to "Aloo Tikki, Chutney, Tea",
            "Dinner" to "Mutton Curry, Roti, Rice"
        ),
        "Sunday" to mutableMapOf(
            "Breakfast" to "Upma, Chutney, Sambar, Tea",
            "Lunch" to "Chicken Biryani, Raita, Salad",
            "Snacks" to "Pav Bhaji, Butter Pav, Tea",
            "Dinner" to "Kofta Curry, Roti, Rice"
        )
    )

    private var selectedDay: String = "Monday"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mess)

        // Initialize the buttons correctly
        btnMonday = findViewById(R.id.btn_monday)
        btnTuesday = findViewById(R.id.btn_tuesday)
        btnWednesday = findViewById(R.id.btn_wednesday)
        btnThursday = findViewById(R.id.btn_thursday)
        btnFriday = findViewById(R.id.btn_friday)
        btnSaturday = findViewById(R.id.btn_saturday)
        btnSunday = findViewById(R.id.btn_sunday)

        // Initialize the TextViews for menu items
        tvBreakfast = findViewById(R.id.tv_breakfast)
        tvLunch = findViewById(R.id.tv_lunch)
        tvSnacks = findViewById(R.id.tv_snacks)
        tvDinner = findViewById(R.id.tv_dinner)

        // Ensure onClickListeners are set for day buttons
        btnMonday.setOnClickListener { updateMenuForDay("Monday") }
        btnTuesday.setOnClickListener { updateMenuForDay("Tuesday") }
        btnWednesday.setOnClickListener { updateMenuForDay("Wednesday") }
        btnThursday.setOnClickListener { updateMenuForDay("Thursday") }
        btnFriday.setOnClickListener { updateMenuForDay("Friday") }
        btnSaturday.setOnClickListener { updateMenuForDay("Saturday") }
        btnSunday.setOnClickListener { updateMenuForDay("Sunday") }

        // Ensure onClickListener is set for the edit button
        findViewById<Button>(R.id.btn_edit_day).setOnClickListener { editMenu() }

        // Initialize with Monday's menu
        updateMenuForDay("Monday")
    }

    private fun updateMenuForDay(day: String) {
        selectedDay = day // Store the selected day

        // Retrieve the menu for the day and update the TextViews
        val menu = menuMap[day]!!
        tvBreakfast.text = menu["Breakfast"]
        tvLunch.text = menu["Lunch"]
        tvSnacks.text = menu["Snacks"]
        tvDinner.text = menu["Dinner"]
    }

    @SuppressLint("MissingInflatedId")
    private fun editMenu() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_menu, null)
        val etBreakfast = dialogView.findViewById<EditText>(R.id.et_breakfast)
        val etLunch = dialogView.findViewById<EditText>(R.id.et_lunch)
        val etSnacks = dialogView.findViewById<EditText>(R.id.et_snacks)
        val etDinner = dialogView.findViewById<EditText>(R.id.et_dinner)

        // Pre-fill the EditTexts with current menu
        etBreakfast.setText(tvBreakfast.text)
        etLunch.setText(tvLunch.text)
        etSnacks.setText(tvSnacks.text)
        etDinner.setText(tvDinner.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Menu")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // Retrieve edited menu items from EditTexts
                val editedBreakfast = etBreakfast.text.toString()
                val editedLunch = etLunch.text.toString()
                val editedSnacks = etSnacks.text.toString()
                val editedDinner = etDinner.text.toString()

                // Update the menu for the selected day in the map
                menuMap[selectedDay]?.set("Breakfast", editedBreakfast)
                menuMap[selectedDay]?.set("Lunch", editedLunch)
                menuMap[selectedDay]?.set("Snacks", editedSnacks)
                menuMap[selectedDay]?.set("Dinner", editedDinner)

                // Update the TextViews with the edited menu
                tvBreakfast.text = editedBreakfast
                tvLunch.text = editedLunch
                tvSnacks.text = editedSnacks
                tvDinner.text = editedDinner
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}