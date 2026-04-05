package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProviderDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_dashboard)

        val addSlotBtn = findViewById<Button>(R.id.addSlotBtn)
        val viewBookingsBtn = findViewById<Button>(R.id.viewBookingsBtn)

        addSlotBtn.setOnClickListener {
            startActivity(Intent(this, AddSlotActivity::class.java))
        }

        viewBookingsBtn.setOnClickListener {
            startActivity(Intent(this, ProviderBookingsActivity::class.java))
        }
    }
}