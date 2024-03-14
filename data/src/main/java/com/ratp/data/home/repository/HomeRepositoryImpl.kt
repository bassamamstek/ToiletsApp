package com.ratp.data.home.repository

import com.ratp.business.common.model.AppError
import com.ratp.business.home.model.ToiletBusinessModel
import com.ratp.business.home.repository.HomeRepository
import com.ratp.business.home.repository.RepositoryResponse
import com.ratp.business.home.repository.RepositorySuccess
import com.ratp.data.common.RepositoryFailureImpl
import com.ratp.data.common.RepositorySuccessImpl
import com.ratp.data.home.datasource.HomeLocalDataSource
import com.ratp.data.home.datasource.HomeRemoteDataSource
import com.ratp.data.home.model.Record
import com.ratp.data.remoteconfig.RemoteConfigRepository

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val remoteConfigRepository: RemoteConfigRepository
) : HomeRepository {
    override suspend fun initialFetchlData(): RepositoryResponse<List<ToiletBusinessModel>> {
        homeLocalDataSource.data?.let { safeLocalData ->
            return RepositorySuccessImpl(safeLocalData)
        }

        return fetchAndCache(0)
    }

    override suspend fun nextFetchData(): RepositoryResponse<List<ToiletBusinessModel>> {
        return fetchAndCache(homeLocalDataSource.lastIndex)
    }

    private suspend fun fetchAndCache(start: Int): RepositoryResponse<List<ToiletBusinessModel>> {
        return when (val result =
            homeRemoteDataSource.fetch(remoteConfigRepository.dataSetUrl, start)) {
            is RepositorySuccess -> {
                val response = result.response.records.map {
                    transformToBusinessModel(it)
                }

                if (start == 0) {
                    homeLocalDataSource.cache(response)
                } else {
                    homeLocalDataSource.add(response)
                }
                homeLocalDataSource.data?.let {
                    RepositorySuccessImpl(it)
                } ?: RepositorySuccessImpl(response)
            }

            is RepositoryFailureImpl -> RepositoryFailureImpl(result.error)
            else -> RepositoryFailureImpl(AppError.UNKNOWN)
        }
    }

    private fun transformToBusinessModel(record: Record): ToiletBusinessModel {
        return ToiletBusinessModel(
            id = record.recordid,
            address = record.fields.address,
            openingHour = record.fields.hourly,
            accessPmr = record.fields.accessPmr,
            location = record.geometry.coordinates
        )
    }
}