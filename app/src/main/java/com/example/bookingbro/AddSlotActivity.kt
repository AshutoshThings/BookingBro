package com.example.bookingbro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSlotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_slot)

        val dateEt = findViewById<EditText>(R.id.dateEt)
        val timeEt = findViewById<EditText>(R.id.timeEt)
        val saveSlotBtn = findViewById<Button>(R.id.saveSlotBtn)

        saveSlotBtn.setOnClickListener {

            val date = dateEt.text.toString().trim()
            val time = timeEt.text.toString().trim()

            if (date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Enter date and time", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Slot Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}