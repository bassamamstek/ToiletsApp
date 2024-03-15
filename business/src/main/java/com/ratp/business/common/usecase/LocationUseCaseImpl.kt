package com.ratp.business.common.usecase

import android.location.Location
import com.ratp.business.common.repository.LocationRepository
import javax.inject.Inject

class LocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
) : LocationUseCase {

    /**
     * get distance in km between location of user and location of destination
     * @currentLocation location of user
     * @addressLocation location of distination
     * @return distance in KM
     */
    override suspend fun getDistance(destination: Location): Float? {
        return locationRepository.lastKnownLocation?.let {
            it.distanceTo(destination) / 1000
        }
    }
}