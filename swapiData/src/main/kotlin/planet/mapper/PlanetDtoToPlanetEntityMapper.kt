package com.kimd13.swapidata.planet.mapper

import com.kimd13.swapidata.mapper.toId
import com.kimd13.swapidata.planet.local.model.PlanetEntity
import com.kimd13.swapidata.planet.remote.model.PlanetDto

internal fun transformToPlanetEntity(
    fromPlanetDto: PlanetDto
): PlanetEntity = with(fromPlanetDto) {
    PlanetEntity(
        id = self_endpoint.toId(),
        name = name,
        population = population
    )
}