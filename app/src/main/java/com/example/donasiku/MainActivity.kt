// MainActivity.kt
package com.example.donasiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    DonasikuApp()  // Memanggil fungsi composable yang sudah didefinisikan di DonasikuApp.kt
                }
            }
        }
    }
}
