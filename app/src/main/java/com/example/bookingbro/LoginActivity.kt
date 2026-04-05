package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEt = findViewById<EditText>(R.id.emailEt)
        val passwordEt = findViewById<EditText>(R.id.passwordEt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val createAccountText = findViewById<TextView>(R.id.createAccountText)

        val selectedRole = intent.getStringExtra("role")

        createAccountText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("role", selectedRole)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {

            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            } else {

                if (selectedRole == "customer") {
                    startActivity(Intent(this, CustomerHomeActivity::class.java))
                } else {
                    startActivity(Intent(this, ProviderDashboardActivity::class.java))
                }
            }
        }
    }
}