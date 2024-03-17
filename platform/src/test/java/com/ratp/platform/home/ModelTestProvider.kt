package com.ratp.platform.home

import com.ratp.business.home.model.ToiletBusinessModel

class ModelTestProvider {
    fun get(size: Int): List<ToiletBusinessModel> {

        val list = mutableListOf<ToiletBusinessModel>()
        for (i in 0 until size) {
            list.add(
                ToiletBusinessModel(
                    id = i.toString(),
                    additionalAddress = "numero_de_voie nom_de_voie",
                    accessPmr = if (i.mod(2) == 0) "Oui" else "Non",
                    openingHour = "H24",
                    address = "$i  AVENUE DE FLANDRE",
                    location = null
                )
            )
        }
        return list
    }
}