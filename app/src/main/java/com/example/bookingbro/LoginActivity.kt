package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEt = findViewById<EditText>(R.id.emailEt)
        val passwordEt = findViewById<EditText>(R.id.passwordEt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val createAccountText = findViewById<TextView>(R.id.createAccountText)

        // 1. Read the Intent Extra (The Sticky Note)
        // We use ?: "customer" as a safe fallback just in case something went wrong
        val selectedRole = intent.getStringExtra("role") ?: "customer"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(AuthApiService::class.java)

        // 2. Pass the role to the Register Activity
        createAccountText.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            registerIntent.putExtra("role", selectedRole)
            startActivity(registerIntent)
        }

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // disable spam clicks
            loginBtn.isEnabled = false
            loginBtn.text = "Logging in..."

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val requestData = LoginRequest(email, password)
                    val response = apiService.loginUser(requestData)

                    withContext(Dispatchers.Main) {
                        loginBtn.isEnabled = true
                        loginBtn.text = "Login"

                        if (response.isSuccessful) {
                            // Extract the user object from the response
                            val user = response.body()?.user
                            val serverRole = user?.role
                            val userName = user?.name ?: "User"

                            // 3. Security Check: Did the server role match the portal they clicked?
                            if (serverRole != selectedRole) {
                                Toast.makeText(this@LoginActivity,
                                    "This account is registered as a $serverRole. Please use the correct portal.",
                                    Toast.LENGTH_LONG).show()
                                return@withContext // Stop them from logging in here
                            }

                            Toast.makeText(this@LoginActivity, "Welcome $userName!", Toast.LENGTH_SHORT).show()

                            // 4. Route them to the correct dashboard
                            if (serverRole == "customer") {
                                startActivity(Intent(this@LoginActivity, CustomerHomeActivity::class.java))
                            } else {
                                startActivity(Intent(this@LoginActivity, ProviderDashboardActivity::class.java))
                            }
                            finish() // Destroy the login screen

                        } else {
                            Toast.makeText(this@LoginActivity, "Login Failed: Wrong email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        loginBtn.isEnabled = true
                        loginBtn.text = "Login"
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}