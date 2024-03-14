package com.ratp.business.home.usecases

import com.ratp.business.common.model.BusinessResponse
import com.ratp.business.home.model.ToiletBusinessModel

interface HomeUseCase {
    suspend fun fetchData(): BusinessResponse<List<ToiletBusinessModel>>
    suspend fun fetchMoreData(): BusinessResponse<List<ToiletBusinessModel>>

}