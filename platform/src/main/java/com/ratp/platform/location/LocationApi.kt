package com.ratp.platform.location

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationApi @Inject constructor(
    private val context: Context,
) {
    suspend fun getCurrentLocation(): Location? {
        val cancellationTokenSource = CancellationTokenSource()
        return try {
            LocationServices.getFusedLocationProviderClient(context).getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await(cancellationTokenSource)
        } catch (e: SecurityException) {
            null
        }
    }
}
