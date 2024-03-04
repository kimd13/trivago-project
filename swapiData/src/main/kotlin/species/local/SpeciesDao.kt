package com.kimd13.swapidata.species.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.species.local.model.SpeciesEntity

@Dao
interface SpeciesDao {

    @Query("SELECT * FROM speciesentity WHERE id=:id")
    suspend fun get(id: Long): SpeciesEntity?

    @Query("SELECT * FROM speciesentity WHERE id IN (:ids)")
    suspend fun get(ids: List<Long>): List<SpeciesEntity>?

    @Upsert
    suspend fun upsert(species: SpeciesEntity)

    @Upsert
    suspend fun upsert(films: List<SpeciesEntity>)

    @Delete
    suspend fun delete(species: SpeciesEntity)
}