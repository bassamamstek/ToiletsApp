package com.ratp.toiletsapp.di

import com.ratp.platform.home.factory.HomeFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltViewModule {

    @Provides
    fun provideHomeFactory() = HomeFactory()
}