package com.example.utspm.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.utspm.ui.screens.*

@Composable
fun NavGraph(
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) + fadeIn(
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500)
                ) + fadeIn(animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            }
        ) {
            composable("home") {
                HomeScreen(
                    onNavigateToMenu = { navController.navigate("menu") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
            composable("menu") {
                MenuScreen(
                    onNavigateToDetail = { menuId ->
                        navController.navigate("menu/$menuId")
                    },
                    onBack = { navController.popBackStack() },
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
            composable("menu/{menuId}") { backStackEntry ->
                val menuId = backStackEntry.arguments?.getString("menuId")
                DetailMenuScreen(
                    menuId = menuId,
                    onBack = { navController.popBackStack() },
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
            composable("profile") {
                ProfileScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToEditProfile = { navController.navigate("edit_profile") },
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
            composable("edit_profile") {
                EditProfileScreen(
                    onBack = { navController.popBackStack() },
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
        }
    }
}
