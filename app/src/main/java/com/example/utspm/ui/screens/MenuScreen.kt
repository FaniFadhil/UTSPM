package com.example.utspm.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.unit.sp
import com.example.utspm.ui.theme.UTSPMTheme

data class MenuItem(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val icon: ImageVector
)

val menuList = listOf(
    MenuItem("1", "Nasi Goreng Spesial", "Rp 25.000", "Nasi goreng lezat dengan telur mata sapi, ayam suwir, dan kerupuk udang yang menggugah selera.", Icons.Default.Fastfood),
    MenuItem("2", "Ayam Bakar Madu", "Rp 30.000", "Ayam bakar empuk dengan olesan madu murni dan bumbu rempah pilihan khas nusantara.", Icons.Default.Fastfood),
    MenuItem("3", "Mie Goreng Seafood", "Rp 28.000", "Mie goreng khas dengan udang segar, cumi, dan bakso ikan melimpah.", Icons.Default.Fastfood),
    MenuItem("4", "Es Teh Manis", "Rp 5.000", "Minuman teh segar berkualitas tinggi dengan gula asli dan es kristal.", Icons.Default.LocalDrink),
    MenuItem("5", "Jus Alpukat Kocok", "Rp 15.000", "Jus alpukat mentega kental dengan siraman susu cokelat premium dan es serut.", Icons.Default.LocalDrink)
)

@Composable
fun MenuScreen(
    onNavigateToDetail: (String) -> Unit,
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    // Latar belakang gradient tetap konsisten dengan gaya Glassmorphism
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
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 48.dp, bottom = 16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Daftar Menu",
                    style = MaterialTheme.typography.headlineMedium,
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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(menuList) { item ->
                    MenuCard(item = item, onClick = { onNavigateToDetail(item.id) })
                }
            }
        }
    }
}

@Composable
fun MenuCard(item: MenuItem, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.White.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Placeholder (sebagai pengganti gambar)
            Surface(
                modifier = Modifier.size(56.dp),
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.padding(14.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    UTSPMTheme {
        MenuScreen(
            onNavigateToDetail = {}, 
            onBack = {},
            isDarkMode = false,
            onToggleTheme = {}
        )
    }
}
