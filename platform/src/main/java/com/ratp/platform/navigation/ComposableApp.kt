package com.ratp.platform.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ratp.platform.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the route of the current screen
    val route = backStackEntry?.destination?.route
    // check route and get screen title, there're only onen screen right now
    val currentScreen = when {
        route?.startsWith(Screens.Home.name) == true -> {
            stringResource(id = Screens.Home.title)
        }

        else -> backStackEntry?.destination?.route ?: stringResource(id = R.string.app_name)
    }

    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = currentScreen) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back
                            )
                        )
                    }
                }
            })
    }, content = { innerPadding ->
        AppNavHost(navController = navController, innerPadding)
    })
}