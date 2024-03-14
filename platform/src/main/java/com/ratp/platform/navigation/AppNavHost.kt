package com.ratp.platform.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ratp.platform.home.ui.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screens.Home.name) {
            HomeScreen()
        }
    }
}