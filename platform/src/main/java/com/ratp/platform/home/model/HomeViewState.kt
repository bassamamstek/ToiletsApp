package com.ratp.platform.home.model

import com.ratp.business.common.model.AppError

data class HomeViewState(
    val homeElements: List<HomeViewElement> = emptyList(),
    val error: AppError? = null,
    val prmFilter: Boolean = false
)