package com.kimd13.swapidata.species

import app.cash.turbine.test
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.speciescore.model.Language
import com.kimd13.speciescore.model.Species
import com.kimd13.swapidata.species.local.SpeciesDao
import com.kimd13.swapidata.species.local.model.SpeciesEntity
import com.kimd13.swapidata.species.remote.SpeciesApi
import com.kimd13.swapidata.species.remote.model.SpeciesDto
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SpeciesRepositoryTest {

    @MockK
    lateinit var api: SpeciesApi

    @MockK
    lateinit var dao: SpeciesDao

    /**
     * Check that all mappings between models below
     * match expectations. Testing focuses on state
     * emissions and assumes mappings were already tested.
     */
    private val speciesId = 1L
    private val speciesName = "Wookie"
    private val speciesLanguage = "Shyriiwook"
    private val speciesDto = SpeciesDto(
        self_endpoint = "https://swapi.dev/api/species/1/",
        name = speciesName,
        language = speciesLanguage
    )
    private val speciesEntity = SpeciesEntity(
        id = speciesId,
        name = speciesName,
        language = speciesLanguage
    )
    private val species = Species(
        id = speciesId,
        name = speciesName,
        language = Language.Recorded(speciesLanguage)
    )

    private val speciesId2 = 2L
    private val species2Name = "Human"
    private val species2Language = "English"
    private val speciesDto2 = SpeciesDto(
        self_endpoint = "https://swapi.dev/api/species/2/",
        name = species2Name,
        language = species2Language
    )
    private val speciesEntity2 = SpeciesEntity(
        id = speciesId2,
        name = species2Name,
        language = species2Language
    )
    private val species2 = Species(
        id = speciesId2,
        name = species2Name,
        language = Language.Recorded(species2Language)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchSpecies given local data exists when remote fetch fails expect Backup`() = runTest {
        // Given
        val repository = SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        val exception = IOException()
        coEvery { dao.get(speciesId) } returns speciesEntity
        coEvery { api.getSpecies(speciesId) } throws exception

        // When
        repository.fetchSpecies(speciesId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Backup(exception, species), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `fetchSpecies given local data does not exist when remote fetch fails expect Failure`() =
        runTest {
            // Given
            val repository =
                SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
            val exception = IOException()
            coEvery { dao.get(speciesId) } returns null
            coEvery { api.getSpecies(speciesId) } throws exception

            // When
            repository.fetchSpecies(speciesId).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Failure(exception), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchSpecies when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        coEvery { api.getSpecies(speciesId) } returns speciesDto
        coEvery { dao.upsert(speciesEntity) } just Runs
        coEvery { dao.get(speciesId) } returns speciesEntity

        // When
        repository.fetchSpecies(speciesId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(species), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(speciesEntity) }
    }

    @Test
    fun `fetchSpecies (plural) given local data exists when remote fetch fails expect Backup`() =
        runTest {
            // Given
            val repository =
                SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
            val exception = IOException()
            val ids = persistentListOf(speciesId, speciesId2)
            coEvery { dao.get(ids) } returns listOf(speciesEntity, speciesEntity2)
            coEvery { api.getSpecies(speciesId) } throws exception

            // When
            repository.fetchSpecies(ids).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Backup(exception, persistentListOf(species, species2)), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchSpecies (plural) given local data does not exist when remote fetch fails expect Failure`() =
        runTest {
            // Given
            val repository =
                SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
            val exception = IOException()
            val ids = persistentListOf(speciesId, speciesId2)
            coEvery { dao.get(ids) } returns null
            coEvery { api.getSpecies(speciesId) } throws exception

            // When
            repository.fetchSpecies(ids).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Failure(exception), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchSpecies (plural) when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = SpeciesRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        val ids = persistentListOf(speciesId, speciesId2)
        val entities = listOf(speciesEntity, speciesEntity2)
        coEvery { api.getSpecies(speciesId) } returns speciesDto
        coEvery { api.getSpecies(speciesId2) } returns speciesDto2
        coEvery { dao.upsert(entities) } just Runs
        coEvery { dao.get(ids) } returns entities

        // When
        repository.fetchSpecies(ids).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(persistentListOf(species, species2)), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(entities) }
    }
}