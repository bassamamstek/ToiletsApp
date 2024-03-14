package com.ratp.data.home.datasource

import com.ratp.data.home.model.ToiletsDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface HomeDataApi {
    /**
     * Fetch Public Toilets
     * @url url of dataset
     * @start fetch from this index of element
     */
    @GET
    suspend fun fetch(@Url url: String, @Query("start") start: Int): Response<ToiletsDataModel>
}