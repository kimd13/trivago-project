package com.kimd13.swapidata.character.remote.model

import com.google.gson.annotations.SerializedName

data class CharactersPageDto(
    val count: String,
    @SerializedName("next")
    val next_endpoint: String?,
    @SerializedName("previous")
    val previous_endpoint: String?,
    val results: List<CharacterDto>
)