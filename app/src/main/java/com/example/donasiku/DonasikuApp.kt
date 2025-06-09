// DonasikuApp.kt
package com.example.donasiku

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.donasiku.ui.screen.HomeScreen
import com.example.donasiku.ui.screen.LoginScreen
import com.example.donasiku.RegisterScreen
import com.example.donasiku.ui.screen.TambahBarangScreen

@Composable
fun DonasikuApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("tambah_barang") {
            TambahBarangScreen(navController)
        }
    }
}
