package com.kimd13.swapidata.planet.remote

import com.kimd13.swapidata.planet.remote.model.PlanetDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanetApi {

    @GET("/api/planets/{id}")
    suspend fun getPlanet(
        @Path("id") id: Long
    ): PlanetDto
}