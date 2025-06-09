package com.example.donasiku.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.donasiku.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    // Ambil instance ViewModel
    val authViewModel: AuthViewModel = viewModel()

    // State input
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Jika login berhasil, navigasi ke halaman home
    val loginResponse = authViewModel.loginResult.value
    LaunchedEffect(loginResponse) {
        loginResponse?.let {
            navController.navigate("home")
        }
    }

    // Tampilan layar login, diletakkan di tengah layar dengan Card
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Judul login
                Text(
                    text = "Welcome Back!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                // Input email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                // Input password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                // Tombol Login
                Button(
                    onClick = { authViewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                // Pesan error (jika ada)
                if (authViewModel.errorMessage.value.isNotEmpty()) {
                    Text(
                        text = authViewModel.errorMessage.value,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                // Tombol navigasi ke halaman registrasi
                TextButton(
                    onClick = { navController.navigate("register") }
                ) {
                    Text("Belum punya akun? Daftar")
                }
            }
        }
    }
}
