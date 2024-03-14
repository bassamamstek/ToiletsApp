package com.ratp.business.home.usecases

import com.ratp.business.common.model.AppError
import com.ratp.business.common.model.BusinessResponse
import com.ratp.business.common.model.Failure
import com.ratp.business.common.model.Success
import com.ratp.business.home.model.ToiletBusinessModel
import com.ratp.business.home.repository.HomeFilterRepository
import com.ratp.business.home.repository.HomeRepository
import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositorySuccess
import javax.inject.Inject

class HomeUsesCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository,
    private val filterRepository: HomeFilterRepository
) : HomeUseCase {

    override suspend fun fetchData(): BusinessResponse<List<ToiletBusinessModel>> {
        return when (val dataResponse = homeRepository.initialFetchlData()) {
            is RepositorySuccess -> {
                Success(applyFilter(dataResponse.response.toList()))
            }

            is RepositoryFailure -> Failure(AppError.UNKNOWN)
        }
    }

    override suspend fun fetchMoreData(): BusinessResponse<List<ToiletBusinessModel>> {
        return when (val dataResponse = homeRepository.fetchMoreData()) {
            is RepositorySuccess -> {
                Success(applyFilter(dataResponse.response.toList()))
            }

            is RepositoryFailure -> Failure(AppError.UNKNOWN)
        }
    }

    private fun applyFilter(data: List<ToiletBusinessModel>): List<ToiletBusinessModel> {
        return if (filterRepository.accessPrm) {
            data.filter {
                it.accessPmr.equals("oui", ignoreCase = true)
            }
        } else {
            data
        }
    }
}