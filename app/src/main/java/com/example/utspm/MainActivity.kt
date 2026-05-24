package com.example.utspm

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.utspm.ui.navigation.NavGraph
import com.example.utspm.ui.theme.UTSPMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simpan data restoran default ke SharedPreferences jika belum ada
        val sharedPref = getSharedPreferences("RestaurantPrefs", Context.MODE_PRIVATE)
        if (!sharedPref.contains("restaurant_name")) {
            sharedPref.edit().apply {
                putString("restaurant_name", "Kedai Sedap Mantap")
                putString("restaurant_address", "Jl. Kuliner No. 123, Jakarta")
                putString("restaurant_description", "Masakan Nusantara & Seafood Terbaik")
                putString("restaurant_hours", "09:00 - 21:00")
                apply()
            }
        }

        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themePref =
                remember { context.getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE) }
            var isDarkMode by remember {
                mutableStateOf(themePref.getBoolean("is_dark_mode", false))
            }

            UTSPMTheme(darkTheme = isDarkMode) {
                NavGraph(
                    isDarkMode = isDarkMode,
                    onToggleTheme = {
                        isDarkMode = !isDarkMode
                        themePref.edit().putBoolean("is_dark_mode", isDarkMode).apply()
                    }
                )
            }
        }
    }
}
