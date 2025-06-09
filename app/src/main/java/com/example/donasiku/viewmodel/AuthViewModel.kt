package com.example.donasiku.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donasiku.models.DonationResponse
import com.example.donasiku.models.LoginResponse
import com.example.donasiku.models.RegisterResponse
import com.example.donasiku.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = UserRepository()

    // State untuk menyimpan hasil login, register dan error message
    val loginResult = mutableStateOf<LoginResponse?>(null)
    val registerResult = mutableStateOf<RegisterResponse?>(null)
    val errorMessage = mutableStateOf("")

    // State untuk daftar donasi
    val donationList = mutableStateOf<List<DonationResponse>>(emptyList())

    // State untuk hasil penambahan donasi
    val addDonationResult = mutableStateOf<Boolean?>(null)

    // Fungsi untuk login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    loginResult.value = response.body()
                } else {
                    errorMessage.value = "Login gagal: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi untuk register
    fun register(name: String, email: String, password: String, alamat: String, noHp: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password, alamat, noHp)
                if (response.isSuccessful) {
                    registerResult.value = response.body()
                } else {
                    errorMessage.value = "Register gagal: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi untuk mereset registerResult
    fun clearRegisterResult() {
        registerResult.value = null
    }

    // Fungsi untuk memuat daftar donasi berdasarkan userId
    fun loadDonations(userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getDonations(userId)
                if (response.isSuccessful) {
                    donationList.value = response.body() ?: emptyList()
                } else {
                    errorMessage.value = "Gagal memuat donasi: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi untuk menambahkan donasi baru
    fun addDonation(userId: Int, namaBarang: String, deskripsi: String, tanggalDonasi: String) {
        viewModelScope.launch {
            try {
                val response = repository.addDonation(userId, namaBarang, deskripsi, tanggalDonasi)
                if (response.isSuccessful) {
                    addDonationResult.value = true
                    loadDonations(userId)
                } else {
                    errorMessage.value = "Tambah donasi gagal: ${response.code()}"
                    addDonationResult.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
                addDonationResult.value = false
            }
        }
    }

    // Fungsi ini untuk mereset state addDonationResult agar efek notifikasi tidak terus berlama-lama
    fun clearAddDonationResult() {
        addDonationResult.value = null
    }
}
