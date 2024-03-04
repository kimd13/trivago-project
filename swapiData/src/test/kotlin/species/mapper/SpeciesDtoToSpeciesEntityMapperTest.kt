package com.kimd13.swapidata.species.mapper

import com.kimd13.swapidata.species.local.model.SpeciesEntity
import com.kimd13.swapidata.species.remote.model.SpeciesDto
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesDtoToSpeciesEntityMapperTest {

    @Test
    fun `transformToSpeciesEntity returns correct entity when given correct dto`() {
        // Given
        val name = "Wookie"
        val language = "Shyriiwook"
        val dto = SpeciesDto(
            self_endpoint = "https://swapi.dev/api/species/1/",
            name = name,
            language = language
        )

        // When
        val entity = transformToSpeciesEntity(dto)

        // Then
        val expectation = SpeciesEntity(
            id = 1,
            name = name,
            language = language
        )
        assertEquals(expectation, entity)
    }
}