package com.example.hostel1.ui.slideshow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hostel1.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private var fileUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupHODSpinner()
        setupSubmitButton()
        setupAttachFileButton()

        return root
    }

    private fun setupHODSpinner() {
        // Populate the HOD spinner
        val hods = listOf("HOD of CS", "HOD of IT", "HOD of ECE")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHOD.adapter = adapter
    }

    private fun setupSubmitButton() {
        // Handle the submit button click
        binding.btnSubmit.setOnClickListener {
            handleFormSubmission()
        }
    }

    private fun setupAttachFileButton() {
        // Allow file selection
        binding.btnAttachFile.setOnClickListener {
            // Launch file picker to choose an image or PDF
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            filePickerLauncher.launch(intent)
        }
    }

    // File picker result callback
    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                fileUri = result.data?.data
                Toast.makeText(requireContext(), "File attached", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun handleFormSubmission() {
        // Extract form inputs
        val selectedHOD = binding.spinnerHOD.selectedItem?.toString() ?: ""
        val name = binding.etName.text.toString().trim()
        val roomNo = binding.etRoomNo.text.toString().trim()
        val noOfDays = binding.etNoOfDays.text.toString().trim()
        val dayOut = binding.etDayOut.text.toString().trim()
        val dayIn = binding.etDayIn.text.toString().trim()
        val reason = binding.etReason.text.toString().trim()
        val studentMobile = binding.etStudentMobileNo.text.toString().trim()
        val parentMobile = binding.etParentsMobileNo.text.toString().trim()
        val leaveAddress = binding.etLeaveAdd.text.toString().trim()
        val hostel = binding.etHostel.text.toString().trim()

        // Validate input fields
        if (listOf(name, roomNo, noOfDays, dayOut, dayIn, reason, studentMobile, parentMobile, leaveAddress, hostel, selectedHOD).any { it.isBlank() }) {
            Toast.makeText(requireContext(), "Please fill all fields and select an HOD", Toast.LENGTH_SHORT).show()
            return
        }

        // Create email content
        val subject = "Leave Application by $name"
        val message = """
            Name: $name
            Room No.: $roomNo
            No. of Days: $noOfDays
            Day Out: $dayOut
            Day In: $dayIn
            Reason: $reason
            Student Mobile: $studentMobile
            Parent Mobile: $parentMobile
            Leave Address: $leaveAddress
            Hostel: $hostel
            Selected HOD: $selectedHOD
        """.trimIndent()

        // Get HOD email based on the selection
        val hodEmail = when (selectedHOD) {
            "HOD of CS" -> "siddharthyadav5142@gmail.com"
            "HOD of IT" -> "it.hod@example.com"
            "HOD of ECE" -> "ece.hod@example.com"
            else -> ""
        }

        Log.d("SlideshowFragment", "Selected HOD Email: $hodEmail")

        if (hodEmail.isBlank()) {
            Toast.makeText(requireContext(), "Invalid HOD selected", Toast.LENGTH_SHORT).show()
            return
        }

        // Send the email with attachment
        sendEmailWithAttachment(hodEmail, subject, message)
    }

    private fun sendEmailWithAttachment(recipient: String, subject: String, message: String) {
        // Ensure a file is attached
        if (fileUri == null) {
            Toast.makeText(requireContext(), "No file selected for attachment", Toast.LENGTH_SHORT).show()
            return
        }

        // Create an email intent with file attachment
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:") // Ensure only email apps can handle it
            type = "application/pdf" // Can be changed depending on the file type (e.g., for images: "image/*")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra(Intent.EXTRA_STREAM, fileUri) // Add the attachment URI
        }

        try {
            // Check if there is an email client available
            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                Toast.makeText(requireContext(), "Email Intent Launched", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Handle exceptions
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
