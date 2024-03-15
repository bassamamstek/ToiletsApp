package com.ratp.platform.home.model

data class HomeViewElement(
    val id: String,
    val address: String,
    val additionalAddress: String,
    val accessPRM: Boolean,
    val openingHour: String,
    val distance: String
)