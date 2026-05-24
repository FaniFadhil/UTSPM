package com.example.utspm.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utspm.ui.theme.UTSPMTheme

@Composable
fun DetailMenuScreen(
    menuId: String?,
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    // Mencari data menu berdasarkan ID yang dikirim dari Navigasi
    val menuItem = menuList.find { it.id == menuId }
    
    val context = LocalContext.current
    val ratingPrefs = remember { context.getSharedPreferences("MenuRatings", Context.MODE_PRIVATE) }

    // State untuk rating interaktif, diinisialisasi dari SharedPreferences
    var rating by remember(menuId) { 
        mutableIntStateOf(ratingPrefs.getInt("rating_$menuId", 0)) 
    }
    
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header: Judul dan Tombol Back
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Menu",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Detail Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggleTheme) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Theme",
                        tint = Color.White
                    )
                }
            }
// ... rest of the file ...

            if (menuItem != null) {
                // Konten Detail dengan Gaya Glassmorphism
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar Besar (Placeholder menggunakan Ikon)
                        Surface(
                            modifier = Modifier.size(200.dp),
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(
                                imageVector = menuItem.icon,
                                contentDescription = null,
                                modifier = Modifier.padding(40.dp),
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Nama Menu
                        Text(
                            text = menuItem.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        // Harga
                        Text(
                            text = menuItem.price,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White.copy(alpha = 0.9f),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Deskripsi (Detail Lengkap)
                        Text(
                            text = menuItem.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.85f),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Rating Bintang Interaktif
                        Text(
                            text = if (rating == 0) "Berikan Rating" else "Rating Anda: $rating/5",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (i in 1..5) {
                                IconButton(onClick = { 
                                    rating = i 
                                    ratingPrefs.edit().putInt("rating_$menuId", i).apply()
                                }) {
                                    Icon(
                                        imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "Bintang $i",
                                        tint = if (i <= rating) Color(0xFFFFD700) else Color.White.copy(alpha = 0.5f),
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Tombol Kembali ke Menu
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.3f),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Kembali ke Menu",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            } else {
                // Fallback jika data tidak ditemukan
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Menu tidak ditemukan", color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("Kembali")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailMenuScreenPreview() {
    UTSPMTheme {
        DetailMenuScreen(
            menuId = "1", 
            onBack = {},
            isDarkMode = false,
            onToggleTheme = {}
        )
    }
}
