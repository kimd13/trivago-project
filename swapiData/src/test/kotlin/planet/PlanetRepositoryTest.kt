package com.kimd13.swapidata.planet

import app.cash.turbine.test
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.model.Population
import com.kimd13.swapidata.planet.local.PlanetDao
import com.kimd13.swapidata.planet.local.model.PlanetEntity
import com.kimd13.swapidata.planet.remote.PlanetApi
import com.kimd13.swapidata.planet.remote.model.PlanetDto
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PlanetRepositoryTest {

    @MockK
    lateinit var api: PlanetApi

    @MockK
    lateinit var dao: PlanetDao

    /**
     * Check that all mappings between models below
     * match expectations. Testing focuses on state
     * emissions and assumes mappings were already tested.
     */
    private val planetId = 1L
    private val planetName = "Earth"
    private val planetPopulation = "120000"
    private val planetDto = PlanetDto(
        self_endpoint = "https://swapi.dev/api/planets/1/",
        name = planetName,
        population = planetPopulation
    )
    private val planetEntity = PlanetEntity(
        id = planetId,
        name = planetName,
        population = planetPopulation
    )
    private val planet = Planet(
        id = planetId,
        name = planetName,
        population = Population.Recorded(120000)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchPlanet given local data exists when remote fetch fails expect Backup`() = runTest {
        // Given
        val repository = PlanetRepositoryImpl(api, dao)
        val exception = IOException()
        coEvery { dao.get(planetId) } returns planetEntity
        coEvery { api.getPlanet(planetId) } throws exception

        // When
        repository.fetchPlanet(planetId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Backup(exception, planet), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `given local data does not exist when remote fetch fails expect Failure`() = runTest {
        // Given
        val repository = PlanetRepositoryImpl(api, dao)
        val exception = IOException()
        coEvery { dao.get(planetId) } returns null
        coEvery { api.getPlanet(planetId) } throws exception

        // When
        repository.fetchPlanet(planetId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Failure(exception), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = PlanetRepositoryImpl(api, dao)
        coEvery { api.getPlanet(planetId) } returns planetDto
        coEvery { dao.upsert(planetEntity) } just Runs
        coEvery { dao.get(planetId) } returns planetEntity

        // When
        repository.fetchPlanet(planetId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(planet), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(planetEntity) }
    }
}