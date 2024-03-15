package com.ratp.business.home.usecases

interface HomeFilterUseCase {
    val accessPrmFilterEnabled: Boolean
    fun enableAccessPrmFilter()
    fun disableAccessPrmFilter()
}