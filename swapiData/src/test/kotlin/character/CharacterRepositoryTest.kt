package com.kimd13.swapidata.character

import app.cash.turbine.test
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit
import com.kimd13.core.Page
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.swapidata.character.local.CharacterDao
import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.character.remote.CharacterApi
import com.kimd13.swapidata.character.remote.model.CharacterDto
import com.kimd13.swapidata.character.remote.model.CharactersPageDto
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterRepositoryTest {

    @MockK
    lateinit var api: CharacterApi

    @MockK
    lateinit var dao: CharacterDao

    /**
     * Check that all mappings between models below
     * match expectations. Testing focuses on state
     * emissions and assumes mappings were already tested.
     */
    private val characterId = 1L
    private val characterName = "Luke Skywalker"
    private val characterHeight = "172"
    private val characterBirthYear = "19BBY"
    private val charactersDto = CharacterDto(
        self_endpoint = "https://swapi.dev/api/people/1/",
        name = characterName,
        height = characterHeight,
        birth_year = characterBirthYear,
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
    private val characterEntity = CharacterEntity(
        id = characterId,
        name = characterName,
        height = characterHeight,
        birthYear = characterBirthYear,
        homeWorldId = 1,
        speciesIds = "1,2",
        filmIds = "1,2"
    )
    private val character = Character(
        id = characterId,
        name = characterName,
        height = Height.Recorded(172, FeetInches(5, 8)),
        birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY),
        imageUrl = null,
        homeWorldId = 1,
        speciesIds = persistentListOf(1, 2),
        filmIds = persistentListOf(1, 2)
    )

    private val query = "Foo"
    private val currentPageNumber = 2
    private val charactersPageDto = CharactersPageDto(
        count = "52",
        next_endpoint = "https://swapi.dev/api/people/?search=a&page=3",
        previous_endpoint = "https://swapi.dev/api/people/?search=a&page=1",
        results = listOf(charactersDto)
    )
    private val charactersPage = Page(
        currentPageNumber = 2,
        nextPageNumber = 3,
        previousPageNumber = 1,
        results = persistentListOf(character)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchCharacter given local data exists when remote fetch fails expect Backup`() =
        runTest {
            // Given
            val repository = CharacterRepositoryImpl(api, dao)
            val exception = IOException()
            coEvery { dao.get(characterId) } returns characterEntity
            coEvery { api.getCharacter(characterId) } throws exception

            // When
            repository.fetchCharacter(characterId).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Backup(exception, character), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchCharacter given local data does not exist when remote fetch fails expect Failure`() =
        runTest {
            // Given
            val repository = CharacterRepositoryImpl(api, dao)
            val exception = IOException()
            coEvery { dao.get(characterId) } returns null
            coEvery { api.getCharacter(characterId) } throws exception

            // When
            repository.fetchCharacter(characterId).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Failure(exception), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchCharacter when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = CharacterRepositoryImpl(api, dao)
        coEvery { api.getCharacter(characterId) } returns charactersDto
        coEvery { dao.upsert(characterEntity) } just Runs
        coEvery { dao.get(characterId) } returns characterEntity

        // When
        repository.fetchCharacter(characterId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(character), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(characterEntity) }
    }

    @Test
    fun `searchCharacter when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = CharacterRepositoryImpl(api, dao)
        val entities = persistentListOf(characterEntity)
        coEvery { api.searchCharacters(query, currentPageNumber) } returns charactersPageDto
        coEvery { dao.upsert(entities) } just Runs

        // When
        repository.searchCharacters(query, currentPageNumber).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(charactersPage), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(entities) }
    }

    @Test
    fun `searchCharacter when remote response fails expect Failure`() = runTest {
        // Given
        val repository = CharacterRepositoryImpl(api, dao)
        val exception = IOException()
        coEvery { api.searchCharacters(query, currentPageNumber) } throws exception

        // When
        repository.searchCharacters(query, currentPageNumber).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Failure(exception), awaitItem())
            awaitComplete()
        }
    }
}