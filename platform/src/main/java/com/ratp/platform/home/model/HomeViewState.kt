package com.ratp.platform.home.model

import com.ratp.business.common.model.AppError
import com.ratp.business.home.model.ToiletBusinessModel

data class HomeViewState(
    val homeElements: List<ToiletBusinessModel> = emptyList(),
    val error: AppError? = null,
)