package com.example.hostel1.ui.Support

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostel1.ui.Support.Rectors
import androidx.lifecycle.ViewModelProvider
import com.example.hostel1.R
import com.example.hostel1.databinding.FragmentSupportHomeBinding

class Support_Home : Fragment() {

    private var _binding: FragmentSupportHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val supportViewModel =
            ViewModelProvider(this)[SupportViewModel::class.java]
        // Inflate the layout for this fragment

        _binding = FragmentSupportHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageButton1.setOnClickListener {
            val intent = Intent(requireActivity(), Warden::class.java)
            startActivity(intent)
        }

        binding.imageButton2.setOnClickListener {
            val intent = Intent(requireActivity(),HODs::class.java)
            startActivity(intent)
        }

        binding.imageButton3.setOnClickListener {
            val intent = Intent(requireActivity(), Sports::class.java)
            startActivity(intent)
        }

        binding.imageButton4.setOnClickListener {
            val intent = Intent(requireActivity(), Rectors::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
