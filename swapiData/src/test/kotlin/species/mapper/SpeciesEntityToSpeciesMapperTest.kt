package com.kimd13.swapidata.species.mapper

import com.kimd13.speciescore.model.Language.Recorded
import com.kimd13.speciescore.model.Language.Unknown
import com.kimd13.speciescore.model.Species
import com.kimd13.swapidata.species.local.model.SpeciesEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesEntityToSpeciesMapperTest {

    @Test
    fun `transformToSpecies returns correct domain model when given correct entity`() {
        // Given
        val id = 1L
        val name = "Wookie"
        val language = "Shyriiwook"
        val entity = SpeciesEntity(
            id = id,
            name = name,
            language = language
        )

        // When
        val domain = transformToSpecies(entity)

        // Then
        val expectation = Species(
            id = id,
            name = name,
            language = Recorded(language)
        )
        assertEquals(expectation, domain)
    }

    @Test
    fun `transformToSpecies returns Unknown when given unknown value`() {
        // Given
        val id = 1L
        val name = "Wookie"
        val entity = SpeciesEntity(
            id = id,
            name = name,
            language = "unknown"
        )

        // When
        val domain = transformToSpecies(entity)

        // Then
        val expectation = Species(
            id = id,
            name = name,
            language = Unknown
        )
        assertEquals(expectation, domain)
    }

    @Test
    fun `transformToSpecies returns Unknown when given null`() {
        // Given
        val id = 1L
        val name = "Wookie"
        val entity = SpeciesEntity(
            id = id,
            name = name,
            language = null
        )

        // When
        val domain = transformToSpecies(entity)

        // Then
        val expectation = Species(
            id = id,
            name = name,
            language = Unknown
        )
        assertEquals(expectation, domain)
    }
}