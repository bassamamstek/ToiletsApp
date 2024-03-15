package com.ratp.toiletsapp.di

import android.app.Application
import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.ratp.business.common.repository.LocationRepository
import com.ratp.platform.home.factory.HomeFactory
import com.ratp.platform.location.LocationApi
import com.ratp.platform.location.LocationRepositoryImpl
import com.ratp.platform.location.PlayServicesAvailabilityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltViewModule {

    @Provides
    fun provideHomeFactory() = HomeFactory()

    @Provides
    @Singleton
    fun providePlayServicesAvailability(
        application: Application
    ) = PlayServicesAvailabilityChecker(application, GoogleApiAvailability.getInstance())

    @Provides
    @Singleton
    fun provideLocationApi(@ApplicationContext application: Context): LocationApi =
        LocationApi(application)

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationApi: LocationApi
    ): LocationRepository = LocationRepositoryImpl(locationApi)

}