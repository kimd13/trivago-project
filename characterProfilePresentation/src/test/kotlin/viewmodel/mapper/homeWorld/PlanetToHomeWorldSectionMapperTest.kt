package com.kimd13.characterprofilepresentation.viewmodel.mapper.homeWorld

import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.model.Population
import org.junit.Assert.assertEquals
import org.junit.Test

class PlanetToHomeWorldSectionMapperTest {

    @Test
    fun `transformToHomeWorldSection returns correct ui model given proper domain model`() {
        // Given
        val name = "Earth"
        val population = Population.Unknown
        val planet = Planet(
            id = 1L,
            name = name,
            population = population
        )

        // When
        val section = transformToHomeWorldSection(planet)

        // Then
        val expectation = HomeWorldSection(
            HomeWorldInfo(
                name = name,
                population = population
            )
        )
        assertEquals(expectation, section)
    }
}