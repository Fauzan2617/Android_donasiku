package com.example.donasiku.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.donasiku.viewmodel.DonationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahLembagaScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    // Gunakan DonationViewModel (atau buat InstitutionViewModel khusus jika diinginkan)
    val donationViewModel: DonationViewModel = viewModel()
    val context = LocalContext.current

    // Monitor hasil tambah lembaga
    val addInstitutionResult by donationViewModel.addInstitutionResult.collectAsState()

    LaunchedEffect(addInstitutionResult) {
        addInstitutionResult?.let { result ->
            if (result) {
                Toast.makeText(context, "Lembaga berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(context, "Gagal menambahkan lembaga: ${donationViewModel.errorMessage.value}", Toast.LENGTH_SHORT).show()
            }
            donationViewModel.clearAddInstitutionResult()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Lembaga") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
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
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Lembaga") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    label = { Text("Kontak") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Button(
                    onClick = {
                        if (name.isBlank()) {
                            Toast.makeText(context, "Nama lembaga wajib diisi!", Toast.LENGTH_SHORT).show()
                        } else {
                            donationViewModel.addInstitution(name, address, contact)
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
