package com.kimd13.swapidata.character.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("url")
    val self_endpoint: String,
    val name: String,
    val height: String,
    val birth_year: String,
    @SerializedName("homeworld")
    val homeworld_endpoint: String,
    @SerializedName("species")
    val species_endpoints: List<String>,
    @SerializedName("films")
    val films_endpoints: List<String>
)