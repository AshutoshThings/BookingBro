package com.example.bookingbro

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProviderBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_bookings)

        val bookingsListView = findViewById<ListView>(R.id.bookingsListView)

        val bookings = arrayOf(
            "Rahul - 10 Apr - 10:00 AM",
            "Aman - 10 Apr - 11:00 AM",
            "Priya - 10 Apr - 12:00 PM"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            bookings
        )

        bookingsListView.adapter = adapter

        bookingsListView.setOnItemClickListener { _, _, position, _ ->

            val selectedBooking = bookings[position]

            AlertDialog.Builder(this)
                .setTitle("Booking Action")
                .setMessage(selectedBooking)
                .setPositiveButton("Confirm") { _, _ ->
                    Toast.makeText(
                        this,
                        "Booking Confirmed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("Reject") { _, _ ->
                    Toast.makeText(
                        this,
                        "Booking Rejected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
        }
    }
}