package com.example.bookingbro

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

}