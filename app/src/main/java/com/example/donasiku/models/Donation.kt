package com.example.donasiku.models

data class Donation(
    val donationId: Int,
    val userId: Int,
    val institutionId: Int?, // Properti baru, nullable bila tidak selalu ada institusi
    val namaBarang: String,
    val deskripsi: String,
    val tanggalDonasi: String, // Bisa juga menggunakan tipe Date sesuai kebutuhan
    val status: String
)
