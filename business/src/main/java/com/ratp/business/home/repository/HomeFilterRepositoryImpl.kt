package com.ratp.business.home.repository

import javax.inject.Inject

class HomeFilterRepositoryImpl @Inject constructor(override var accessPrm: Boolean = false) :
    HomeFilterRepository