package com.example.donasiku.models

data class DonationResponse(
    val donationId: Int,
    val userId: Int,
    val institutionId: Int?,
    val namaBarang: String,
    val deskripsi: String,
    val tanggalDonasi: String,
    val status: String
)
