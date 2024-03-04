package com.kimd13.swapidata.character.mapper

import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.character.remote.model.CharacterDto
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterDtoToCharacterEntityMapperTest {

    @Test
    fun `transformToCharacterEntity returns correct entity when given correct dto`() {
        // Given
        val name = "Luke Skywalker"
        val height = "172"
        val birthYear = "19BBY"
        val dto = CharacterDto(
            self_endpoint = "https://swapi.dev/api/people/1/",
            name = name,
            height = height,
            birth_year = birthYear,
            homeworld_endpoint = "https://swapi.dev/api/planets/1/",
            species_endpoints = listOf(
                "https://swapi.dev/api/species/1/",
                "https://swapi.dev/api/species/2/"
            ),
            films_endpoints = listOf(
                "https://swapi.dev/api/films/1/",
                "https://swapi.dev/api/films/2/"
            )
        )

        // When
        val entity = transformToCharacterEntity(dto)

        // Then
        val expectation = CharacterEntity(
            id = 1,
            name = name,
            height = height,
            birthYear = birthYear,
            homeWorldId = 1,
            speciesIds = "1,2",
            filmIds = "1,2"
        )
        assertEquals(expectation, entity)
    }
}