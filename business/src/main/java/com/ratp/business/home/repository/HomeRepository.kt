package com.ratp.business.home.repository

import com.ratp.business.home.model.ToiletBusinessModel

interface HomeRepository {
    suspend fun initialFetchlData(): RepositoryResponse<List<ToiletBusinessModel>>
    suspend fun nextFetchData(): RepositoryResponse<List<ToiletBusinessModel>>

}