package com.kimd13.swapidata.film.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val openingCrawl: String
)