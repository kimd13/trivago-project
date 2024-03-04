package com.kimd13.planetcore.repository

import com.kimd13.core.Response
import com.kimd13.planetcore.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {

    fun fetchPlanet(
        id: Long
    ): Flow<Response<Planet>>
}