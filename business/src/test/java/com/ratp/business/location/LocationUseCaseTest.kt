package com.ratp.business.location

import android.location.Location
import com.ratp.business.common.repository.LocationRepository
import com.ratp.business.common.usecase.LocationUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LocationUseCaseTest {
    private val locationRepository: LocationRepository = mockk()
    private val currentLocation: Location = mockk()

    private val locationUseCase =
        LocationUseCaseImpl(locationRepository)

    @Test
    fun GIVEN_user_s_location_is_known_WHEN_getDistance_is_called_THEN_distance_is_calculated() =
        runTest {
            // given
            every { currentLocation.distanceTo(any()) } returns 1000f
            every { locationRepository.lastKnownLocation } returns currentLocation

            // when
            val result = locationUseCase.getDistance(Location(""))

            // then
            TestCase.assertEquals(result, 1f)

        }

    @Test
    fun GIVEN_user_s_location_is_unknown_WHEN_getDistance_is_called_THEN_distance_is_null() =
        runTest {
            // given
            every { currentLocation.distanceTo(any()) } returns 1000f
            every { locationRepository.lastKnownLocation } returns null

            // when
            val result = locationUseCase.getDistance(Location(""))

            // then
            TestCase.assertNull(result)

        }
}