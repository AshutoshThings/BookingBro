package com.example.bookingbro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val selectedRole = intent.getStringExtra("role") ?: "customer"

        if (selectedRole == "customer") {
            businessNameEt.visibility = View.GONE
            categoryEt.visibility = View.GONE
            locationEt.visibility = View.GONE
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(AuthApiService::class.java)

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

            registerBtn.isEnabled = false
            registerBtn.text = "Creating Account..."

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val requestData = RegisterRequest(
                        full_name = name,
                        email = email,
                        phone = phone,
                        password = password,
                        role = selectedRole
                    )

                    val response = apiService.registerUser(requestData)

                    withContext(Dispatchers.Main) {
                        registerBtn.isEnabled = true
                        registerBtn.text = "Register"

                        if (response.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Account Created Successfully!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            intent.putExtra("role", selectedRole)
                            startActivity(intent)
                            finish()

                        } else if (response.code() == 409) {
                            Toast.makeText(this@RegisterActivity, "Registration failed: Email or phone number already in use.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        registerBtn.isEnabled = true
                        registerBtn.text = "Register"
                        Toast.makeText(this@RegisterActivity, "Network Error: Cannot reach server", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}