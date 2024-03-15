package com.ratp.business.home.model

import android.location.Location

data class ToiletBusinessModel(
    val id: String,
    val address: String,
    val additionalAddress: String,
    val openingHour: String,
    val accessPmr: String,
    val location: Location?
)