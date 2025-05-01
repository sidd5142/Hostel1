package com.example.hostel1.Complaint.RegisterComp.Status

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel1.Integration.Complain
import com.example.hostel1.R

class ComplaintListAdapter(private val complaints: List<Complain>) :
    RecyclerView.Adapter<ComplaintListAdapter.ComplaintViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = complaints[position]
        holder.bind(complaint)
    }

    override fun getItemCount(): Int = complaints.size

    class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        private val textViewType: TextView = itemView.findViewById(R.id.textViewType)
        private val textViewRoomNo: TextView = itemView.findViewById(R.id.textViewRoomNo)
        private val textViewProblem: TextView = itemView.findViewById(R.id.textViewProblem)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewMailId: TextView = itemView.findViewById(R.id.textViewMailId)
        private val buttonPending: Button = itemView.findViewById(R.id.buttonPending)
        private val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)

        @SuppressLint("SetTextI18n")
        fun bind(complaint: Complain) {
            textViewName.text = "Name: ${complaint.student.std_name}"
            textViewDate.text = "Date: ${complaint.created_date}" // If date/time is not in JSON, set default or hide
            textViewTime.text = "Time: ${complaint.created_time}"
            textViewType.text = "Type: ${complaint.type}"
            textViewRoomNo.text = "Room No: ${complaint.student.room_altd}"
            textViewProblem.text = "Problem: ${complaint.problem}"
            textViewDescription.text = "Description: ${complaint.description}"
            textViewMailId.text = complaint.student.email

            buttonPending.setOnClickListener {
                // TODO: Handle pending button click
            }

            buttonEdit.setOnClickListener {
                // TODO: Handle edit button click
            }
        }
    }
}
