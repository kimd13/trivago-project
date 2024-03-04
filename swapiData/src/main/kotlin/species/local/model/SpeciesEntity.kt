package com.kimd13.swapidata.species.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpeciesEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val language: String?
)