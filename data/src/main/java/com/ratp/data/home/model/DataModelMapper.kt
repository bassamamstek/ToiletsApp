package com.ratp.data.home.model

import android.location.Location
import com.ratp.business.home.model.ToiletBusinessModel

fun ToiletsDataModel.toBusinessModel(): List<ToiletBusinessModel> {
    return records.map {
        ToiletBusinessModel(
            id = it.recordid,
            address = it.fields.address ?: "",
            additionalAddress = it.fields.additionalAddress ?: "",
            openingHour = it.fields.hourly ?: "",
            accessPmr = it.fields.accessPmr ?: "",
            location = it.geometry.coordinates?.let { coordinates ->
                Location(null).apply {
                    latitude = coordinates.first()
                    longitude = coordinates.last()
                }
            }
        )
    }
}