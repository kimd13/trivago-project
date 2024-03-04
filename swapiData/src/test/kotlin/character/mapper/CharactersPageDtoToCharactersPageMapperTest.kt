package com.kimd13.swapidata.character.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit
import com.kimd13.core.Page
import com.kimd13.core.mapImmutable
import com.kimd13.swapidata.character.remote.model.CharacterDto
import com.kimd13.swapidata.character.remote.model.CharactersPageDto
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class CharactersPageDtoToCharactersPageMapperTest {

    @Test
    fun `transformToCharactersPage returns correct domain model when given correct dto`() {
        // Given
        val name = "Luke Skywalker"
        val previousPageNumber = 1
        val currentPageNumber = 2
        val nextPageNumber = 3
        val results = listOf(
            CharacterDto(
                self_endpoint = "https://swapi.dev/api/people/1/",
                name = name,
                height = "172",
                birth_year = "19BBY",
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
        )
        val dto = CharactersPageDto(
            count = "52",
            next_endpoint = "https://swapi.dev/api/people/?search=a&page=$nextPageNumber",
            previous_endpoint = "https://swapi.dev/api/people/?search=a&page=$previousPageNumber",
            results = results
        )
        val entities = results.mapImmutable(::transformToCharacterEntity)

        // When
        val domain = transformToCharactersPage(
            fromCharactersPageDto = dto,
            currentPageNumber = currentPageNumber,
            withCharacterEntities = entities
        )

        // Then
        val expectation = Page(
            currentPageNumber = currentPageNumber,
            nextPageNumber = nextPageNumber,
            previousPageNumber = previousPageNumber,
            results = persistentListOf(
                Character(
                    id = 1,
                    name = name,
                    height = Height.Recorded(172, FeetInches(5, 8)),
                    birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY),
                    imageUrl = null,
                    homeWorldId = 1,
                    speciesIds = persistentListOf(1, 2),
                    filmIds = persistentListOf(1, 2)
                )
            )
        )
        assertEquals(expectation, domain)
    }
}