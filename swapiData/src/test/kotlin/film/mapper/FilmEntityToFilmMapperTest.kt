package com.kimd13.swapidata.film.mapper

import com.kimd13.filmcore.model.Film
import com.kimd13.swapidata.film.local.model.FilmEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class FilmEntityToFilmMapperTest {

    @Test
    fun `transformToFilm returns correct domain model when given correct entity`() {
        // Given
        val id = 1L
        val title = "A New Hope"
        val openingCrawl = "A long, long, long time ago..."
        val entity = FilmEntity(
            id = id,
            title = title,
            openingCrawl = openingCrawl
        )

        // When
        val domain = transformToFilm(entity)

        // Then
        val expectation = Film(
            id = id,
            title = title,
            openingCrawl = openingCrawl
        )
        assertEquals(expectation, domain)
    }
}