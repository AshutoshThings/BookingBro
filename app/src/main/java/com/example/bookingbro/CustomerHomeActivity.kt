package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class CustomerHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home)

        val providerListView = findViewById<ListView>(R.id.providerListView)

        val providers = arrayListOf(
            Provider("Rahul Barber Shop", "Barber", "Ghaziabad"),
            Provider("City Clinic", "Doctor", "Noida"),
            Provider("Style Salon", "Salon", "Delhi")
        )

        val providerNames = providers.map {
            "${it.name}\n${it.category} • ${it.location}"
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            providerNames
        )

        providerListView.adapter = adapter

        providerListView.setOnItemClickListener { _, _, position, _ ->

            val provider = providers[position]

            val intent = Intent(this, ProviderDetailsActivity::class.java)
            intent.putExtra("provider_name", provider.name)
            intent.putExtra("provider_category", provider.category)
            intent.putExtra("provider_location", provider.location)

            startActivity(intent)
        }
    }
}