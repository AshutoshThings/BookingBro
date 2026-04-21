package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Find the Name and Email TextViews
        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)

        // 2. Unpack the Bundle
        val name = arguments?.getString("USER_NAME") ?: "User"
        val email = arguments?.getString("USER_EMAIL") ?: "name@gmail.com"

        // 3. Set the text
        tvProfileName.text = name
        tvProfileEmail.text = email

        // --- NEW LOGOUT LOGIC BELOW ---

        // 4. Find the logout button
        val logoutBtn = view.findViewById<TextView>(R.id.logoutbtn)

        // 5. Listen for the click
        logoutBtn.setOnClickListener {
            // Create an Intent to go back to MainActivity (or LoginActivity)
            // We use 'requireActivity()' to get the context from the Fragment
            val intent = Intent(requireActivity(), MainActivity::class.java)

            // This shreds the history so the user can't press "Back" to undo the logout
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Start the new activity
            startActivity(intent)
        }
    }
}