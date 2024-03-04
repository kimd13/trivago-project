package com.kimd13.swapidata.planet.mapper

import com.kimd13.swapidata.planet.local.model.PlanetEntity
import com.kimd13.swapidata.planet.remote.model.PlanetDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PlanetDtoToPlanetEntityMapperTest {

    @Test
    fun `transformToPlanetEntity returns correct entity when given correct dto`() {
        // Given
        val name = "Earth"
        val population = "120000"
        val dto = PlanetDto(
            self_endpoint = "https://swapi.dev/api/planets/1/",
            name = name,
            population = population
        )

        // When
        val entity = transformToPlanetEntity(dto)

        // Then
        val expectation = PlanetEntity(
            id = 1,
            name = name,
            population = population
        )
        assertEquals(expectation, entity)
    }
}