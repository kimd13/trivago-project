package com.kimd13.characterprofilepresentation.viewmodel.mapper.films

import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.filmcore.model.Film
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class FilmsToFilmsSectionMapperTest {

    @Test
    fun `transformToFilmsSection returns correct ui model given proper domain model`() {
        // Given
        val id = 1L
        val title = "Title"
        val openingCrawl = "A long, long time ago"
        val film = Film(
            id = id,
            title = title,
            openingCrawl = openingCrawl
        )

        // When
        val section = transformToFilmsSection(persistentListOf(film))

        // Then
        val expectation = FilmsSection(
            persistentListOf(
                FilmInfo(
                    id = id,
                    title = title,
                    openingCrawl = openingCrawl
                )
            )
        )
        assertEquals(expectation, section)
    }
}