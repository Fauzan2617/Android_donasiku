package com.example.donasiku.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donasiku.models.DonationRequest
import com.example.donasiku.models.DonationResponse
import com.example.donasiku.models.InstitutionRequest
import com.example.donasiku.models.InstitutionResponse
import com.example.donasiku.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DonationViewModel : ViewModel() {

    // State untuk menampung hasil tambah donasi
    private val _addDonationResult = MutableStateFlow<Boolean?>(null)
    val addDonationResult: StateFlow<Boolean?> = _addDonationResult

    // State untuk menampung daftar donasi user
    private val _donations = MutableStateFlow<List<DonationResponse>>(emptyList())
    val donations: StateFlow<List<DonationResponse>> = _donations

    // State untuk menampung daftar institutions dari API
    private val _institutions = MutableStateFlow<List<InstitutionResponse>>(emptyList())
    val institutions: StateFlow<List<InstitutionResponse>> = _institutions

    // State untuk menampung result dari penambahan institution
    private val _addInstitutionResult = MutableStateFlow<Boolean?>(null)
    val addInstitutionResult: StateFlow<Boolean?> = _addInstitutionResult

    // State untuk menampung error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addDonation(userId: Int, institutionId: Int?, namaBarang: String, deskripsi: String, tanggalDonasi: String) {
        viewModelScope.launch {
            try {
                val request = DonationRequest(userId, institutionId, namaBarang, deskripsi, tanggalDonasi)
                val response = RetrofitClient.apiService.addDonation(request)

                if (response.isSuccessful && response.body() != null) {
                    _addDonationResult.value = true
                    Log.d("DonationViewModel", "Donasi berhasil ditambahkan: ${response.body()}")
                } else {
                    _addDonationResult.value = false
                    _errorMessage.value = "Gagal menambahkan donasi: ${response.message()}"
                    Log.e("DonationViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _addDonationResult.value = false
                _errorMessage.value = "Terjadi error saat menambahkan donasi: ${e.message}"
                Log.e("DonationViewModel", "Exception terjadi: ${e.message}")
            }
        }
    }


    fun getDonations(userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getDonations(userId)
                if (response.isSuccessful && response.body() != null) {
                    _donations.value = response.body()!!
                    Log.d("DonationViewModel", "Donasi berhasil diambil: ${response.body()}")
                } else {
                    _errorMessage.value = "Gagal mengambil daftar donasi: ${response.message()}"
                    Log.e("DonationViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi error saat mengambil donasi: ${e.message}"
                Log.e("DonationViewModel", "Exception terjadi: ${e.message}")
            }
        }
    }

    fun getInstitutions() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getInstitutions()
                if (response.isSuccessful && response.body() != null) {
                    _institutions.value = response.body()!!
                    Log.d("DonationViewModel", "Institutions berhasil diambil: ${response.body()}")
                } else {
                    _errorMessage.value = "Gagal mengambil daftar institutions: ${response.message()}"
                    Log.e("DonationViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi error saat mengambil institutions: ${e.message}"
                Log.e("DonationViewModel", "Exception terjadi: ${e.message}")
            }
        }
    }

    fun addInstitution(name: String, address: String, contact: String?) {
        viewModelScope.launch {
            try {
                val request = InstitutionRequest(name = name, address = address, contact = contact)
                val response = RetrofitClient.apiService.addInstitution(request)
                if (response.isSuccessful && response.body() != null) {
                    _addInstitutionResult.value = true
                    Log.d("DonationViewModel", "Institution berhasil ditambahkan: ${response.body()}")
                } else {
                    _addInstitutionResult.value = false
                    _errorMessage.value = "Gagal menambahkan institution: ${response.message()}"
                    Log.e("DonationViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _addInstitutionResult.value = false
                _errorMessage.value = "Terjadi error saat menambahkan institution: ${e.message}"
                Log.e("DonationViewModel", "Exception terjadi saat addInstitution: ${e.message}")
            }
        }
    }

    fun clearAddDonationResult() {
        _addDonationResult.value = null
    }

    // Opsional: fungsi clear untuk institution jika diperlukan
    fun clearAddInstitutionResult() {
        _addInstitutionResult.value = null
    }
}
