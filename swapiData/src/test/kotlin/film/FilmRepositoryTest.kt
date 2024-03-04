package com.kimd13.swapidata.film

import app.cash.turbine.test
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.filmcore.model.Film
import com.kimd13.swapidata.film.local.FilmDao
import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.film.remote.FilmApi
import com.kimd13.swapidata.film.remote.model.FilmDto
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
class FilmRepositoryTest {

    @MockK
    lateinit var api: FilmApi

    @MockK
    lateinit var dao: FilmDao

    /**
     * Check that all mappings between models below
     * match expectations. Testing focuses on state
     * emissions and assumes mappings were already tested.
     */
    private val filmId = 1L
    private val filmTitle = "A New Hope"
    private val filmOpeningCrawl = "A long, long, long time ago..."
    private val filmDto = FilmDto(
        self_endpoint = "https://swapi.dev/api/films/1/",
        title = filmTitle,
        opening_crawl = filmOpeningCrawl
    )
    private val filmEntity = FilmEntity(
        id = filmId,
        title = filmTitle,
        openingCrawl = filmOpeningCrawl
    )
    private val film = Film(
        id = filmId,
        title = filmTitle,
        openingCrawl = filmOpeningCrawl
    )

    private val filmId2 = 2L
    private val film2Title = "A New Hope Par 2"
    private val film2OpeningCrawl = "A long, long, long time ago continued..."
    private val filmDto2 = FilmDto(
        self_endpoint = "https://swapi.dev/api/films/2/",
        title = film2Title,
        opening_crawl = film2OpeningCrawl
    )
    private val filmEntity2 = FilmEntity(
        id = filmId2,
        title = film2Title,
        openingCrawl = film2OpeningCrawl
    )
    private val film2 = Film(
        id = filmId2,
        title = film2Title,
        openingCrawl = film2OpeningCrawl
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchFilm given local data exists when remote fetch fails expect Backup`() = runTest {
        // Given
        val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        val exception = IOException()
        coEvery { dao.get(filmId) } returns filmEntity
        coEvery { api.getFilm(filmId) } throws exception

        // When
        repository.fetchFilm(filmId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Backup(exception, film), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `fetchFilm given local data does not exist when remote fetch fails expect Failure`() =
        runTest {
            // Given
            val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
            val exception = IOException()
            coEvery { dao.get(filmId) } returns null
            coEvery { api.getFilm(filmId) } throws exception

            // When
            repository.fetchFilm(filmId).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Failure(exception), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchFilm when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        coEvery { api.getFilm(filmId) } returns filmDto
        coEvery { dao.upsert(filmEntity) } just Runs
        coEvery { dao.get(filmId) } returns filmEntity

        // When
        repository.fetchFilm(filmId).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(film), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(filmEntity) }
    }

    @Test
    fun `fetchFilms given local data exists when remote fetch fails expect Backup`() = runTest {
        // Given
        val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        val exception = IOException()
        val ids = persistentListOf(filmId, filmId2)
        coEvery { dao.get(ids) } returns listOf(filmEntity, filmEntity2)
        coEvery { api.getFilm(filmId) } throws exception

        // When
        repository.fetchFilms(ids).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Backup(exception, persistentListOf(film, film2)), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `fetchFilms given local data does not exist when remote fetch fails expect Failure`() =
        runTest {
            // Given
            val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
            val exception = IOException()
            val ids = persistentListOf(filmId, filmId2)
            coEvery { dao.get(ids) } returns null
            coEvery { api.getFilm(filmId) } throws exception

            // When
            repository.fetchFilms(ids).test {

                // Then
                assertEquals(Loading, awaitItem())
                assertEquals(Failure(exception), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `fetchFilms when remote response succeeds expect results to match`() = runTest {
        // Given
        val repository = FilmRepositoryImpl(api, dao, UnconfinedTestDispatcher(testScheduler))
        val ids = persistentListOf(filmId, filmId2)
        val entities = listOf(filmEntity, filmEntity2)
        coEvery { api.getFilm(filmId) } returns filmDto
        coEvery { api.getFilm(filmId2) } returns filmDto2
        coEvery { dao.upsert(entities) } just Runs
        coEvery { dao.get(ids) } returns entities

        // When
        repository.fetchFilms(ids).test {

            // Then
            assertEquals(Loading, awaitItem())
            assertEquals(Success(persistentListOf(film, film2)), awaitItem())
            awaitComplete()
        }

        coVerify { dao.upsert(entities) }
    }
}