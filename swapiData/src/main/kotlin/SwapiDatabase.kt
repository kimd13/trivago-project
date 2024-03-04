package com.kimd13.swapidata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kimd13.swapidata.character.local.CharacterDao
import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.film.local.FilmDao
import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.planet.local.PlanetDao
import com.kimd13.swapidata.planet.local.model.PlanetEntity
import com.kimd13.swapidata.species.local.SpeciesDao
import com.kimd13.swapidata.species.local.model.SpeciesEntity

@Database(
    entities = [
        CharacterEntity::class,
        FilmEntity::class,
        PlanetEntity::class,
        SpeciesEntity::class
    ],
    version = 1
)
abstract class SwapiDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun filmDao(): FilmDao
    abstract fun planetDao(): PlanetDao
    abstract fun speciesDao(): SpeciesDao
}