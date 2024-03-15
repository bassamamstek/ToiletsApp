package com.ratp.business.common.usecase

import android.location.Location

interface LocationUseCase {

    /**
     * Get distance in km between location of user and location of destination
     * @destination location of distination
     * @return diantance in KM
     */
    suspend fun getDistance(destination: Location): Float?
}