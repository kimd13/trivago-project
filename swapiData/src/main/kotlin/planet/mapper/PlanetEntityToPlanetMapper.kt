package com.kimd13.swapidata.planet.mapper

import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.model.Population
import com.kimd13.swapidata.planet.local.model.PlanetEntity

internal fun transformToPlanet(
    fromPlanetEntity: PlanetEntity
): Planet = with(fromPlanetEntity) {
    Planet(
        id = id,
        name = name,
        population = population.toPopulation()
    )
}

private fun String.toPopulation() = if (this == "unknown") {
    Population.Unknown
} else {
    Population.Recorded(toLong())
}