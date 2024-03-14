package com.ratp.data.remoteconfig

import com.ratp.business.gateway.RemoteConfigGateway

class RemoteConfigRepository : RemoteConfigGateway {
    override val dataSetUrl =
        "https://data.ratp.fr/api/records/1.0/search/?dataset=sanisettesparis2011&start=0&rows=100"
}