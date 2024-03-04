package com.kimd13.swapidata.film.mapper

import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.film.remote.model.FilmDto
import org.junit.Assert.assertEquals
import org.junit.Test

class FilmDtoToFilmEntityMapperTest {

    @Test
    fun `transformToFilmEntity returns correct entity when given correct dto`() {
        // Given
        val title = "A New Hope"
        val openingCrawl = "A long, long, long time ago..."
        val dto = FilmDto(
            self_endpoint = "https://swapi.dev/api/films/1/",
            title = title,
            opening_crawl = openingCrawl
        )

        // When
        val entity = transformToFilmEntity(dto)

        // Then
        val expectation = FilmEntity(
            id = 1,
            title = title,
            openingCrawl = openingCrawl
        )
        assertEquals(expectation, entity)
    }
}