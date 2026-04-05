package com.example.bookingbro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEt = findViewById<EditText>(R.id.nameEt)
        val emailEt = findViewById<EditText>(R.id.emailEt)
        val phoneEt = findViewById<EditText>(R.id.phoneEt)
        val passwordEt = findViewById<EditText>(R.id.passwordEt)

        val businessNameEt = findViewById<EditText>(R.id.businessNameEt)
        val categoryEt = findViewById<EditText>(R.id.categoryEt)
        val locationEt = findViewById<EditText>(R.id.locationEt)

        val registerBtn = findViewById<Button>(R.id.registerBtn)

        val selectedRole = intent.getStringExtra("role")

        if (selectedRole == "customer") {
            businessNameEt.visibility = View.GONE
            categoryEt.visibility = View.GONE
            locationEt.visibility = View.GONE
        }

        registerBtn.setOnClickListener {

            val name = nameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedRole == "provider") {

                val businessName = businessNameEt.text.toString().trim()
                val category = categoryEt.text.toString().trim()
                val location = locationEt.text.toString().trim()

                if (businessName.isEmpty() || category.isEmpty() || location.isEmpty()) {
                    Toast.makeText(this, "Please fill provider details", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("role", selectedRole)
            startActivity(intent)
            finish()
            finish()
        }
    }
}