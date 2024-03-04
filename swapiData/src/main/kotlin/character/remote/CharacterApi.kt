package com.kimd13.swapidata.character.remote

import com.kimd13.swapidata.character.remote.model.CharacterDto
import com.kimd13.swapidata.character.remote.model.CharactersPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/people/{id}")
    suspend fun getCharacter(
        @Path("id") id: Long
    ): CharacterDto

    @GET("/api/people/")
    suspend fun searchCharacters(
        @Query("search") name: String,
        @Query("page") page: Int
    ): CharactersPageDto
}