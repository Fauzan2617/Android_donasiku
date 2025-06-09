package com.example.donasiku.models

data class InstitutionResponse(
    val institutionId: Int,
    val name: String,
    val address: String?,
    val contact: String?
)
