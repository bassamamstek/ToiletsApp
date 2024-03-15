package com.ratp.platform

import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ratp.platform.location.LocationPermissionState
import com.ratp.platform.navigation.ComposableApp
import com.ratp.platform.ui.theme.ToiletsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationManager: LocationManager
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationPermissionState = LocationPermissionState(this) {
            if (it.accessFineLocationGranted) {
                viewModel.getCurrentLocation()
            }
        }
        setContent {
            ToiletsAppTheme {
                MainScreen(viewModel, locationPermissionState)
            }
        }

    }


    @Composable
    fun MainScreen(
        viewModel: MainViewModel,
        locationPermissionState: LocationPermissionState
    ) {
        val uiState by viewModel.uiState.collectAsState()
        when (uiState.state) {
            UiState.Initializing -> Text(text = "")
            UiState.PlayServicesUnavailable -> ComposableApp()
            UiState.PlayServicesAvailable -> {
                locationPermissionState.requestPermissions()
            }

            UiState.LocationCalled -> ComposableApp()
        }
    }
}