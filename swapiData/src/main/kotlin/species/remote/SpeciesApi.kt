package com.kimd13.swapidata.species.remote

import com.kimd13.swapidata.species.remote.model.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SpeciesApi {

    @GET("/api/species/{id}")
    suspend fun getSpecies(
        @Path("id") id: Long
    ): SpeciesDto
}