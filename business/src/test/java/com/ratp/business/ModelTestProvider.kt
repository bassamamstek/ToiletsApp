package com.ratp.business

import android.location.Location
import com.ratp.business.home.model.ToiletBusinessModel
import io.mockk.mockk

class ModelTestProvider {

    private val location: Location = mockk()

    fun get(size: Int): List<ToiletBusinessModel> {

        val list = mutableListOf<ToiletBusinessModel>()
        for (i in 0..size) {
            list.add(
                ToiletBusinessModel(
                    id = i.toString(),
                    additionalAddress = "numero_de_voie nom_de_voie",
                    accessPmr = if (i.mod(2) == 0) "Oui" else "Non",
                    openingHour = "H24",
                    address = "$i  AVENUE DE FLANDRE",
                    location = location
                )
            )
        }
        return list
    }
}