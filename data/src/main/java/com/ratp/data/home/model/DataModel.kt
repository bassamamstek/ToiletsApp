package com.ratp.data.home.model

import com.google.gson.annotations.SerializedName

data class GeoShape(
    val coordinates: List<List<Double>>,
    val type: String
)

data class Fields(
    @SerializedName("complement_adresse") val additionalAddress: String,
    @SerializedName("geo_shape") val geoShape: GeoShape,
    @SerializedName("horaire") val hourly: String,
    @SerializedName("acces_pmr") val accessPmr: String,
    @SerializedName("arrondissement") val district: Int,
    @SerializedName("geo_point_2d") val geoPoint2d: List<Double>,
    val source: String,
    @SerializedName("gestionnaire") val administrator: String,
    @SerializedName("adresse") val address: String,
    val type: String
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

data class Record(
    val datasetid: String,
    val recordid: String,
    val fields: Fields,
    val geometry: Geometry,
    @SerializedName("record_timestamp") val recordTimestamp: String
)

data class Parameters(
    val dataset: String,
    val rows: Int,
    val start: Int,
    val format: String,
    val timezone: String
)

data class ToiletsDataModel(
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Record>
)