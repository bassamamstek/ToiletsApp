package com.ratp.data.home.datasource

import com.ratp.business.home.model.ToiletBusinessModel

class HomeLocalDataSource {
    var data: MutableList<ToiletBusinessModel>? = null

    val lastIndex get() = data?.size ?: 0
    fun cache(remoteData: List<ToiletBusinessModel>) {
        data = remoteData.toMutableList()
    }

    fun add(remoteData: List<ToiletBusinessModel>) {
        data?.run {
            addAll(remoteData)
        } ?: kotlin.run {
            data = remoteData.toMutableList()
        }
    }


}