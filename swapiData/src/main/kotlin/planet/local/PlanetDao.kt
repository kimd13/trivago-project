package com.kimd13.swapidata.planet.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kimd13.swapidata.planet.local.model.PlanetEntity

@Dao
interface PlanetDao {

    @Query("SELECT * FROM planetentity WHERE id=:id")
    suspend fun get(id: Long): PlanetEntity?

    @Upsert
    suspend fun upsert(planet: PlanetEntity)

    @Delete
    suspend fun delete(planet: PlanetEntity)
}