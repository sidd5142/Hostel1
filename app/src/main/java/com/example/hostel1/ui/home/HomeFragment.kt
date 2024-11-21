package com.example.hostel1.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hostel1.Complaint.RegisterComp.Registration
import com.example.hostel1.Integration.API_Service
import com.example.hostel1.Integration.RetrofitClient
import com.example.hostel1.Mess.MessActivity
import com.example.hostel1.R
import com.example.hostel1.databinding.FragmentHomeBinding
import com.example.hostel1.register.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment<T> : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var apiService: API_Service

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RetrofitClient with the fragment's context
        RetrofitClient.init(requireContext())
        apiService = RetrofitClient.apiService

        // Set up the toolbar
        setHasOptionsMenu(true)

        sharedPreferences = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Use the binding to reference views and set data
        homeViewModel.text.observe(viewLifecycleOwner) {
            // Example: binding.textHome.text = it
        }

        binding.imagebtBp.setOnClickListener {
            val intent = Intent(requireActivity(), Registration::class.java)
            startActivity(intent)
        }

        binding.imagebtAttend.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_attendance)
        }

        binding.imagebtMess.setOnClickListener {
            startActivity(Intent(requireActivity(), MessActivity::class.java))
        }

        binding.imageCouncil.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_support)
        }

        binding.imagebtLeave.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_slideshow)
        }

        return root
    }

    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.nav_home, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Clear cookies from SharedPreferences
//        val editor = sharedPreferences.edit()
////        editor.remove("cookies")
//        editor.apply()

        println("LogOut id : ${getCookies()}")
        val sessionCookies = getCookies()
        if (!sessionCookies.isNullOrEmpty()) {
            val cookieHeader = sessionCookies.joinToString("; ")

            RetrofitClient.apiService.makePostLogOut(cookieHeader).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Logout successful")
                        val editor = sharedPreferences.edit()
                        editor.remove("cookies")
                        editor.apply()
                        // Successful logout, navigate to login screen
                        navigateToLogin()
                    } else {
                        Log.d(TAG, "Logout failed: ${response.code()}")
                        // Handle logout failure
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e(TAG, "Logout request failed", t)
                    // Handle network failures or exceptions
                }
            })
        } else {
            Log.e(TAG, "No session cookies found")
            // Even if no cookies are found, navigate to login
//            navigateToLogin()
        }
    }

    private fun getCookies(): List<String>? {
        val cookieSet = sharedPreferences.getStringSet("cookies", null)
        return cookieSet?.toList()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
