package com.example.utspm.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.utspm.ui.theme.UTSPMTheme

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("RestaurantPrefs", Context.MODE_PRIVATE) }
    
    var restaurantName by remember { 
        mutableStateOf(sharedPref.getString("restaurant_name", "Kedai Sedap Mantap") ?: "Kedai Sedap Mantap") 
    }
    var restaurantAddress by remember { 
        mutableStateOf(sharedPref.getString("restaurant_address", "Jl. Kuliner No. 123, Jakarta") ?: "Jl. Kuliner No. 123, Jakarta") 
    }
    var restaurantDesc by remember { 
        mutableStateOf(sharedPref.getString("restaurant_description", "Masakan Nusantara & Seafood Terbaik") ?: "Masakan Nusantara & Seafood Terbaik") 
    }
    var restaurantHours by remember { 
        mutableStateOf(sharedPref.getString("restaurant_hours", "09:00 - 21:00") ?: "09:00 - 21:00") 
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
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Edit Profil",
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

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EditField(
                        label = "Nama Restoran",
                        value = restaurantName,
                        onValueChange = { restaurantName = it }
                    )
                    
                    EditField(
                        label = "Alamat",
                        value = restaurantAddress,
                        onValueChange = { restaurantAddress = it }
                    )
                    
                    EditField(
                        label = "Deskripsi Singkat",
                        value = restaurantDesc,
                        onValueChange = { restaurantDesc = it }
                    )
                    
                    EditField(
                        label = "Jam Buka",
                        value = restaurantHours,
                        onValueChange = { restaurantHours = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            sharedPref.edit().apply {
                                putString("restaurant_name", restaurantName)
                                putString("restaurant_address", restaurantAddress)
                                putString("restaurant_description", restaurantDesc)
                                putString("restaurant_hours", restaurantHours)
                                apply()
                            }
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.9f),
                            contentColor = if (isDarkMode) Color(0xFF1F1B24) else Color(0xFF6200EE)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("Batal", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    UTSPMTheme {
        EditProfileScreen(
            onBack = {},
            isDarkMode = false,
            onToggleTheme = {}
        )
    }
}
