package com.kimd13.swapidata.planet.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val population: String
)