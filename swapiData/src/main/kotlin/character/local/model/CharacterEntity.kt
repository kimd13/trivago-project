package com.kimd13.swapidata.character.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val height: String,
    val birthYear: String,
    val homeWorldId: Long,
    val speciesIds: String,
    val filmIds: String
)