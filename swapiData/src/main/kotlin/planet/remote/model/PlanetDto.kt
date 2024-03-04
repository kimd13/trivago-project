package com.kimd13.swapidata.planet.remote.model

import com.google.gson.annotations.SerializedName

data class PlanetDto(
    @SerializedName("url")
    val self_endpoint: String,
    val name: String,
    val population: String
)