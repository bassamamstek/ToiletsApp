package com.ratp.data

import com.ratp.data.home.model.Fields
import com.ratp.data.home.model.GeoShape
import com.ratp.data.home.model.Geometry
import com.ratp.data.home.model.Parameters
import com.ratp.data.home.model.Record
import com.ratp.data.home.model.ToiletsDataModel

class ModelTestProvider {
    fun get(size: Int, start: Int): ToiletsDataModel {

        val records = mutableListOf<Record>()
        for (i in start..size) {
            records.add(
                Record(
                    datasetid = "sanisettesparis2011",
                    recordid = i.toString(),
                    fields = Fields(
                        additionalAddress = "numero_de_voie nom_de_voie",
                        geoShape = GeoShape(
                            coordinates = listOf(listOf(2.3739240719106323, 48.88869536274599)),
                            type = "MultiPoint"
                        ),
                        hourly = "24 h / 24",
                        accessPmr = "Oui",
                        district = 75019,
                        geoPoint2d = listOf(
                            48.88869536274599,
                            2.3739240719106323
                        ),
                        source = "http://opendata.paris.fr",
                        administrator = "Toilette publique de la Ville de Paris",
                        address = "$i  AVENUE DE FLANDRE",
                        type = "SANISETTE"
                    ),
                    geometry = Geometry(
                        type = "Point",
                        coordinates = listOf(
                            2.3739240719106323,
                            48.88869536274599
                        )
                    ),
                    recordTimestamp = "2024-03-04T05:12:00.478Z"
                )
            )
        }
        return ToiletsDataModel(
            nhits = size,
            parameters = Parameters(
                dataset = "sanisettesparis2011",
                rows = 3,
                start = 0,
                format = "json",
                timezone = "UTC"
            ),
            records = records
        )
    }
}