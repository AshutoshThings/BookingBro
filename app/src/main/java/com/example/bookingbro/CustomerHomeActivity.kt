package com.example.bookingbro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerHomeActivity : AppCompatActivity() {

    // 1. Create variables to hold the data while the Activity is alive
    private var loggedInUserName: String = "User"
    private var loggedInUserEmail: String = "user@example.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home)

        // 2. Catch the "sticky notes" passed from LoginActivity
        loggedInUserName = intent.getStringExtra("USER_NAME") ?: "User"
        loggedInUserEmail = intent.getStringExtra("USER_EMAIL") ?: "user@example.com"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Load the HomeFragment as the default screen when the app opens
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            homeFragment.arguments = Bundle().apply {
                putString("USER_NAME", loggedInUserName)
            }
            replaceFragment(homeFragment)
        }

        // Handle clicks on the bottom navigation bar
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val homeFragment = HomeFragment()
                    homeFragment.arguments = Bundle().apply {
                        putString("USER_NAME", loggedInUserName)
                    }
                    replaceFragment(homeFragment)
                    true
                }
                R.id.nav_profile -> {
                    val profileFragment = ProfileFragment()
                    profileFragment.arguments = Bundle().apply {
                        // 3. Pack BOTH the name and the email into the Bundle for the profile screen!
                        putString("USER_NAME", loggedInUserName)
                        putString("USER_EMAIL", loggedInUserEmail)
                    }
                    replaceFragment(profileFragment)
                    true
                }
                // If you have more menu items, add them here!
                else -> false
            }
        }

    }

    // This function does the actual swapping of the screens
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}