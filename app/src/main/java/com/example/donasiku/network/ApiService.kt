package com.example.donasiku.network

import com.example.donasiku.models.DonationRequest
import com.example.donasiku.models.DonationResponse
import com.example.donasiku.models.InstitutionRequest
import com.example.donasiku.models.InstitutionResponse
import com.example.donasiku.models.LoginRequest
import com.example.donasiku.models.LoginResponse
import com.example.donasiku.models.RegisterRequest
import com.example.donasiku.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST

interface ApiService {

    @GET("donations/{userId}")
    suspend fun getDonations(@Path("userId") userId: Int): Response<List<DonationResponse>>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("donations")
    suspend fun addDonation(@Body request: DonationRequest): Response<DonationResponse>

    @GET("institutions")
    suspend fun getInstitutions(): Response<List<InstitutionResponse>>

    // Endpoint untuk menambahkan lembaga (institution)
    @POST("institutions")
    suspend fun addInstitution(@Body request: InstitutionRequest): Response<InstitutionResponse>
}
