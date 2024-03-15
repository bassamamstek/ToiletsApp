package com.ratp.platform.home.factory

import com.ratp.business.home.model.ToiletBusinessModel
import com.ratp.platform.home.model.HomeViewElement

class HomeFactory {
    fun generateViewElement(element: ToiletBusinessModel, distance: Float?): HomeViewElement {
        return HomeViewElement(
            id = element.id,
            address = element.address,
            additionalAddress = element.additionalAddress,
            openingHour = "Horaires d'ouverture : ${element.openingHour}",
            accessPRM = element.accessPmr.equals("oui", ignoreCase = true),
            distance = distance?.let {
                "A $distance km"
            } ?: ""
        )
    }
}