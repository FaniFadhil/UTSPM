package com.example.utspm.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utspm.ui.theme.UTSPMTheme

@Composable
fun HomeScreen(
    onNavigateToMenu: () -> Unit,
    onNavigateToProfile: () -> Unit,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("RestaurantPrefs", Context.MODE_PRIVATE)
    }
    // Tidak menggunakan remember agar selalu mengambil nilai terbaru dari SharedPreferences
    val restaurantName = sharedPreferences.getString("restaurant_name", "Kedai Sedap Mantap") ?: "Kedai Sedap Mantap"

    // Latar belakang gradient cerah agar efek glass terlihat kontras
    val backgroundGradient = Brush.verticalGradient(
        colors = if (isDarkMode) {
            listOf(Color(0xFF121212), Color(0xFF1F1B24))
        } else {
            listOf(Color(0xFF6200EE), Color(0xFF03DAC5))
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Theme Toggle Button di pojok kanan atas
        IconButton(
            onClick = onToggleTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Toggle Theme",
                tint = Color.White
            )
        }

        // Kontainer Glassmorphism
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            color = Color.White.copy(alpha = 0.2f), // Transparansi efek kaca
            shape = RoundedCornerShape(28.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)), // Border tipis berkilau
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 48.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo Restoran
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = "Logo Restoran",
                    modifier = Modifier.size(100.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sapaan
                Text(
                    text = "Selamat Datang di",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = restaurantName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(56.dp))

                // Tombol Lihat Menu (Gaya Solid Transparan)
                Button(
                    onClick = onNavigateToMenu,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.9f),
                        contentColor = if (isDarkMode) Color(0xFF1F1B24) else Color(0xFF6200EE)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Lihat Menu", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Profil (Gaya Outlined Putih)
                OutlinedButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.5.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Profil Restoran", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Glass Home Preview")
@Composable
fun HomeScreenPreview() {
    UTSPMTheme {
        HomeScreen(
            onNavigateToMenu = {}, 
            onNavigateToProfile = {},
            isDarkMode = false,
            onToggleTheme = {}
        )
    }
}
