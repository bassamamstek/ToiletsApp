package com.ratp.data.home.datasource

import com.ratp.data.home.model.ToiletsDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface HomeDataApi {
    @GET
    suspend fun fetch(@Url url: String): Response<List<ToiletsDataModel>>
}