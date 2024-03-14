package com.ratp.toiletsapp.di

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

@Module
@InstallIn(SingletonComponent::class)
object HiltBusinessModule {

    @Provides
    fun provideHomeFilterRepository() = HomeFilterRepositoryImpl()

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
}