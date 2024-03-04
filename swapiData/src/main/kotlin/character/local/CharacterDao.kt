package com.kimd13.swapidata.character.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kimd13.swapidata.character.local.model.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterentity WHERE id=:id")
    suspend fun get(id: Long): CharacterEntity?

    @Upsert
    suspend fun upsert(character: CharacterEntity)

    @Upsert
    suspend fun upsert(characters: List<CharacterEntity>)

    @Delete
    suspend fun delete(character: CharacterEntity)
}