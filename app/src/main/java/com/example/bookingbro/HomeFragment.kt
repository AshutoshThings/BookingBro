package com.example.bookingbro

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Fetch and set the user's name from the Bundle
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val name = arguments?.getString("USER_NAME") ?: "User"
        tvUserName.text = name

        // 2. Find the two appointment cards by their new IDs
        val cardAppointment1 = view.findViewById<LinearLayout>(R.id.cardAppointment1)
        val cardAppointment2 = view.findViewById<LinearLayout>(R.id.cardAppointment2)

        // 3. Listen for clicks on the FIRST card (Classic Cuts)
        cardAppointment1.setOnClickListener {
            // Build the confirmation pop-up
            AlertDialog.Builder(requireContext())
                .setTitle("Cancel Appointment")
                .setMessage("Are you sure you want to cancel your appointment at Classic Cuts Barbershop?")
                .setPositiveButton("Yes, Cancel") { dialog, _ ->
                    // Remove the card from the screen completely
                    cardAppointment1.visibility = View.GONE
                    Toast.makeText(requireContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Keep it") { dialog, _ ->
                    // Dismiss the pop-up, do nothing
                    dialog.dismiss()
                }
                .show()
        }

        // 4. Listen for clicks on the SECOND card (Bright Smile Dental)
        cardAppointment2.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Cancel Appointment")
                .setMessage("Are you sure you want to cancel your appointment at Bright Smile Dental?")
                .setPositiveButton("Yes, Cancel") { dialog, _ ->

                    cardAppointment2.visibility = View.GONE
                    Toast.makeText(requireContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Keep it") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}