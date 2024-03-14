package com.ratp.data.home.datasource

import com.ratp.business.common.model.AppError
import com.ratp.business.home.repository.RepositoryResponse
import com.ratp.data.common.RepositoryFailureImpl
import com.ratp.data.common.RepositorySuccessImpl
import com.ratp.data.home.model.ToiletsDataModel

class HomeRemoteDataSource(private val homeDataApi: HomeDataApi) {
    suspend fun fetch(url: String, start: Int): RepositoryResponse<ToiletsDataModel> {
        return try {
            val homeResponse = homeDataApi.fetch(url, start)
            return when {
                homeResponse.isSuccessful -> {
                    homeResponse.body()?.let { safeResponseBody ->
                        RepositorySuccessImpl(safeResponseBody)
                    } ?: run { RepositoryFailureImpl(AppError.EMPTY) }
                }

                else -> {
                    RepositoryFailureImpl(AppError.NETWORK_ERROR)
                }
            }
        } catch (e: Exception) {
            RepositoryFailureImpl(AppError.UNKNOWN)
        }
    }
}