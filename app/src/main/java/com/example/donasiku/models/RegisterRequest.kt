package com.example.donasiku.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val alamat: String,
    val noHp: String
)
