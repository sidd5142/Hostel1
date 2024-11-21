package com.example.hostel1.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import com.example.hostel1.Integration.Test
import com.example.hostel1.databinding.FragmentSlideshowBinding
import com.example.hostel1.ui.attendance.Attend_Marking

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSlideshow
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(requireContext(), Attend_Marking::class.java)
            startActivity(intent)
        }
        return root


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}