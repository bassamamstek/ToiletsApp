package com.ratp.platform.location

import android.location.Location
import com.ratp.business.common.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
) : LocationRepository {

    private var lastLocation: Location? = null
    override val lastKnownLocation: Location?
        get() = lastLocation

    override suspend fun getCurrentLocation(): Location? {
        locationApi.getCurrentLocation()?.let {
            lastLocation = it
        }
        return lastLocation
    }
}