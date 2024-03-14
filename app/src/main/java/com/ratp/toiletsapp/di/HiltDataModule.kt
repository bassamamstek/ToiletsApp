package com.ratp.toiletsapp.di

import com.ratp.business.gateway.RemoteConfigGateway
import com.ratp.business.home.repository.HomeRepository
import com.ratp.data.home.datasource.HomeDataApi
import com.ratp.data.home.datasource.HomeLocalDataSource
import com.ratp.data.home.datasource.HomeRemoteDataSource
import com.ratp.data.home.repository.HomeRepositoryImpl
import com.ratp.data.remoteconfig.RemoteConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClientClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        remoteConfigRepository: RemoteConfigGateway
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(remoteConfigRepository.dataSetUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHomeDataApi(retrofitClient: Retrofit): HomeDataApi {
        return retrofitClient.create(HomeDataApi::class.java)
    }

    @Provides
    fun provideHomeRemoteDataSource(homeDataApi: HomeDataApi) = HomeRemoteDataSource(homeDataApi)

    @Provides
    @Singleton
    fun provideHomeLocalDataSource() = HomeLocalDataSource()

    @Provides
    @Singleton
    fun provideRemoteConfigRepository(): RemoteConfigGateway = RemoteConfigRepository()

    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        homeLocalDataSource: HomeLocalDataSource,
        remoteConfigRepository: RemoteConfigGateway
    ): HomeRepository =
        HomeRepositoryImpl(homeRemoteDataSource, homeLocalDataSource, remoteConfigRepository)
}
