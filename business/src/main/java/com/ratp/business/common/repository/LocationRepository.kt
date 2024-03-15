package com.ratp.business.common.repository

import android.location.Location

interface LocationRepository {
    val lastKnownLocation: Location?
    suspend fun getCurrentLocation(): Location?
}