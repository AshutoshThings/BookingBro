package com.example.bookingbro
data class LoginRequest(
    val emailInput: String,
    val passwordInput: String
)
data class LoginResponse(
    val message: String,
    val user: UserData?
)

data class UserData(
    val id: String,
    val name: String,
    val role: String
)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String
)

data class RegisterResponse(
    val message: String,
    val user: UserData?
)