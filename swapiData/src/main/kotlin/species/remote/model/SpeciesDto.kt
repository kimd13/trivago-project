package com.kimd13.swapidata.species.remote.model

import com.google.gson.annotations.SerializedName

data class SpeciesDto(
    @SerializedName("url")
    val self_endpoint: String,
    val name: String,
    val language: String?
)