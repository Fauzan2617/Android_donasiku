package com.example.donasiku.models

data class LoginResponse(
    val token: String, // misalnya token login
    val userId: Int,
    val name: String,
    val email: String
)
