package com.example.hostel1.ui.attendance

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hostel1.R
import com.example.hostel1.databinding.FragmentAttendHomeBinding

class Attend_Home : Fragment() {

    private var _binding: FragmentAttendHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAttendHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.imageButton1.setOnClickListener {
            findNavController().navigate(R.id.action_nav_attendance_to_datePicker)
        }

        binding.imageButton2.setOnClickListener {
            findNavController().navigate(R.id.action_nav_attendance_to_absentDate)
        }
        return view
//        startActivity(Intent(requireActivity(),Absent_list::class.java))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
