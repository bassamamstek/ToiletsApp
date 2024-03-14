package com.ratp.business.home.usecases

import com.ratp.business.home.repository.HomeFilterRepository
import javax.inject.Inject

class HomeFilterUseCaseImpl @Inject constructor(
    private val homeFilterRepository: HomeFilterRepository
) : HomeFilterUseCase {
    override fun enableAccessPrmFilter() {
        homeFilterRepository.accessPrm = true
    }

    override fun disableAccessPrmFilter() {
        homeFilterRepository.accessPrm = false
    }
}