package com.kimd13.swapidata.film.remote.model

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("url")
    val self_endpoint: String,
    val title: String,
    val opening_crawl: String
)