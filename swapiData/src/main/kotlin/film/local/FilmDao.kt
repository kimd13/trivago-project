package com.kimd13.swapidata.film.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kimd13.swapidata.film.local.model.FilmEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM filmentity WHERE id=:id")
    suspend fun get(id: Long): FilmEntity?

    @Query("SELECT * FROM filmentity WHERE id IN (:ids)")
    suspend fun get(ids: List<Long>): List<FilmEntity>?

    @Upsert
    suspend fun upsert(film: FilmEntity)

    @Upsert
    suspend fun upsert(films: List<FilmEntity>)

    @Delete
    suspend fun delete(film: FilmEntity)
}