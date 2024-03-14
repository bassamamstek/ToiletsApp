package com.ratp.platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ratp.platform.navigation.ComposableApp
import com.ratp.platform.ui.theme.ToiletsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToiletsAppTheme {
                ComposableApp()
            }
        }
    }
}