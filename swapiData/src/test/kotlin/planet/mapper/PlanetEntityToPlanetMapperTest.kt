package com.kimd13.swapidata.planet.mapper

import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.model.Population.Recorded
import com.kimd13.planetcore.model.Population.Unknown
import com.kimd13.swapidata.planet.local.model.PlanetEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PlanetEntityToPlanetMapperTest {

    @Test
    fun `transformToPlanet returns correct domain model when given correct entity`() {
        // Given
        val id = 1L
        val name = "Earth"
        val entity = PlanetEntity(
            id = id,
            name = name,
            population = "120000"
        )

        // When
        val domain = transformToPlanet(entity)

        // Then
        val expectation = Planet(
            id = id,
            name = name,
            population = Recorded(120000)
        )
        assertEquals(expectation, domain)
    }

    @Test
    fun `transformToPlanet returns Unknown when given unknown value`() {
        // Given
        val id = 1L
        val name = "Earth"
        val entity = PlanetEntity(
            id = id,
            name = name,
            population = "unknown"
        )

        // When
        val domain = transformToPlanet(entity)

        // Then
        val expectation = Planet(
            id = id,
            name = name,
            population = Unknown
        )
        assertEquals(expectation, domain)
    }
}