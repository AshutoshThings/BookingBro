package com.example.bookingbro

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProviderDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_details)

        val providerNameText = findViewById<TextView>(R.id.providerNameText)
        val slotListView = findViewById<ListView>(R.id.slotListView)

        val providerName = intent.getStringExtra("provider_name")
        val providerCategory = intent.getStringExtra("provider_category")
        val providerLocation = intent.getStringExtra("provider_location")

        providerNameText.text =
            "$providerName\n$providerCategory • $providerLocation"

        val slots = arrayOf(
            "10:00 AM - 10:30 AM",
            "11:00 AM - 11:30 AM",
            "12:00 PM - 12:30 PM",
            "2:00 PM - 2:30 PM"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            slots
        )

        slotListView.adapter = adapter

        slotListView.setOnItemClickListener { _, _, position, _ ->

            val selectedSlot = slots[position]

            Toast.makeText(
                this,
                "Booked $selectedSlot with $providerName",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}