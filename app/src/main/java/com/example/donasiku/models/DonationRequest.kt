package com.example.donasiku.models

data class DonationRequest(
    val userId: Int,
    val institutionId: Int?,  // Nullable jika tidak wajib
    val namaBarang: String,
    val deskripsi: String,
    val tanggalDonasi: String  // Format "yyyy-MM-dd" atau sesuai kebutuhan
)
