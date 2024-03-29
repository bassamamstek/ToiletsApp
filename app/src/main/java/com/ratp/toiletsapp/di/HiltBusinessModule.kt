package com.ratp.toiletsapp.di

import com.ratp.business.common.repository.LocationRepository
import com.ratp.business.common.usecase.LocationUseCase
import com.ratp.business.common.usecase.LocationUseCaseImpl
import com.ratp.business.home.repository.HomeFilterRepository
import com.ratp.business.home.repository.HomeFilterRepositoryImpl
import com.ratp.business.home.repository.HomeRepository
import com.ratp.business.home.usecases.HomeFilterUseCase
import com.ratp.business.home.usecases.HomeFilterUseCaseImpl
import com.ratp.business.home.usecases.HomeUseCase
import com.ratp.business.home.usecases.HomeUsesCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltBusinessModule {

    @Provides
    @Singleton
    fun provideHomeFilterRepository(): HomeFilterRepository = HomeFilterRepositoryImpl()

    @Provides
    fun provideHomeFilterUseCase(
        homeFilterRepository: HomeFilterRepository
    ): HomeFilterUseCase =
        HomeFilterUseCaseImpl(homeFilterRepository)

    @Provides
    fun provideHomeUsesCase(
        homeRepository: HomeRepository,
        homeFilterRepository: HomeFilterRepository
    ): HomeUseCase =
        HomeUsesCaseImpl(homeRepository, homeFilterRepository)

    @Provides
    fun provideDistanceUsesCase(
        locationRepository: LocationRepository
    ): LocationUseCase =
        LocationUseCaseImpl(locationRepository)


}