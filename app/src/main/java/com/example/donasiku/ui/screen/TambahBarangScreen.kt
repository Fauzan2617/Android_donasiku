package com.example.donasiku.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.donasiku.models.InstitutionResponse
import com.example.donasiku.viewmodel.DonationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahBarangScreen(navController: NavController) {
    var namaBarang by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var selectedInstitution by remember { mutableStateOf<InstitutionResponse?>(null) }

    val donationViewModel: DonationViewModel = viewModel()
    val context = LocalContext.current

    val institutions by donationViewModel.institutions.collectAsState()


    // Ambil daftar institutions saat pertama kali membuka halaman
    LaunchedEffect(Unit) {
        donationViewModel.getInstitutions()
    }

    LaunchedEffect(donationViewModel.addDonationResult.collectAsState().value) {
        donationViewModel.addDonationResult.value?.let { result ->
            if (result) {
                Toast.makeText(context, "Donasi berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Gagal menambahkan donasi: ${donationViewModel.errorMessage.value}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            donationViewModel.clearAddDonationResult()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Barang") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tambah Donasi",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )

                        OutlinedTextField(
                            value = namaBarang,
                            onValueChange = { namaBarang = it },
                            label = { Text("Nama Barang") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = deskripsi,
                            onValueChange = { deskripsi = it },
                            label = { Text("Deskripsi") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Dropdown untuk memilih Institution jika tersedia
                        var expanded by remember { mutableStateOf(false) }
                        if (institutions.isNotEmpty()) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                OutlinedButton(
                                    onClick = { expanded = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(selectedInstitution?.name ?: "Pilih Lembaga")
                                }
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    institutions.forEach { institution ->
                                        DropdownMenuItem(
                                            text = { Text(institution.name) },
                                            onClick = {
                                                selectedInstitution = institution
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        } else {
                            Text("Tidak ada lembaga tersedia", color = MaterialTheme.colorScheme.error)
                        }

                        Button(
                            onClick = {
                                if (namaBarang.isBlank() || deskripsi.isBlank()) {
                                    Toast.makeText(context, "Harap isi nama barang dan deskripsi", Toast.LENGTH_SHORT).show()
                                } else {
                                    val userId =  1
                                    val institutionId = selectedInstitution?.institutionId

                                    Log.d("TambahBarangScreen", "Mengirim donasi: userId=$userId, institutionId=$institutionId, namaBarang=$namaBarang")
                                    donationViewModel.addDonation(userId, institutionId, namaBarang, deskripsi, "2025-06-08")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Simpan")
                        }
                    }
                }
            }
        }
    }
}
