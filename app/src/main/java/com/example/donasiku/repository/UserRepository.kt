package com.example.donasiku.repository

import com.example.donasiku.models.DonationRequest
import com.example.donasiku.models.DonationResponse
import com.example.donasiku.models.LoginRequest
import com.example.donasiku.models.LoginResponse
import com.example.donasiku.models.RegisterRequest
import com.example.donasiku.models.RegisterResponse
import com.example.donasiku.network.RetrofitClient
import retrofit2.Response

class UserRepository {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return RetrofitClient.apiService.loginUser(request)
    }

    suspend fun getDonations(userId: Int): Response<List<DonationResponse>> {
        return RetrofitClient.apiService.getDonations(userId)
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        alamat: String,
        noHp: String
    ): Response<RegisterResponse> {
        val request = RegisterRequest(name, email, password, alamat, noHp)
        return RetrofitClient.apiService.registerUser(request)
    }

    suspend fun addDonation(
        userId: Int,
        namaBarang: String,
        deskripsi: String,
        tanggalDonasi: String
    ): Response<DonationResponse> {
        // Misal userId diambil dari variabel atau passed parameter; di sini kita memakai contoh userId = 1
        val request = DonationRequest(
            userId = 1,               // pastikan ini bertipe Int
            institutionId = 1,     // atau ganti dengan id lembaga yang valid (Int) jika ada
            namaBarang = namaBarang,
            deskripsi = deskripsi,
            tanggalDonasi = tanggalDonasi
        )
        return RetrofitClient.apiService.addDonation(request)
    }
}
