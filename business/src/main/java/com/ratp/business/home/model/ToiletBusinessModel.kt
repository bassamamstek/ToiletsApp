package com.ratp.business.home.model

data class ToiletBusinessModel(
    val id: String,
    val address: String,
    val openingHour: String,
    val prmAccess: Boolean,
    val distance: Long
)