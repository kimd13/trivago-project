package com.kimd13.homepresentation.viewmodel.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit
import com.kimd13.core.Page
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription
import com.kimd13.homepresentation.viewmodel.model.HomePage
import io.mockk.mockk
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CharactersPageToHomePageMapperTest {

    @Test
    fun `transformToHomePage returns correct ui model given proper domain model`() {
        // Given
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Recorded(172, FeetInches(5, 8))
        val birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY)
        val currentPageNumber = 2
        val nextPageNumber = 3
        val previousPageNumber = 1
        val nextPage = {}
        val previousPage = {}
        val results: ImmutableList<Character> = persistentListOf(
            Character(
                id = id,
                name = name,
                height = height,
                birthYear = birthYear,
                imageUrl = null,
                homeWorldId = 1,
                speciesIds = persistentListOf(),
                filmIds = persistentListOf()
            )
        )
        val page = Page(
            currentPageNumber = currentPageNumber,
            nextPageNumber = nextPageNumber,
            previousPageNumber = previousPageNumber,
            results = results
        )

        // When
        val homePage = transformToHomePage(
            page = page,
            nextPage = nextPage,
            previousPage = previousPage
        )

        // Then
        val expectation = HomePage(
            number = currentPageNumber,
            nextPageExists = true,
            nextPage = nextPage,
            previousPageExists = true,
            previousPage = previousPage,
            characters = persistentListOf(
                HomeCharacter(
                    id = id,
                    name = name,
                    description = HomeCharacterDescription.CompleteDescription(
                        birthYear = birthYear,
                        height = height
                    )
                )
            )
        )
        assertEquals(expectation, homePage)
    }

    @Test
    fun `transformToHomePage returns safely wrapped next and previous functions given pages don't exist`() {
        // Given
        val nextPage = {}
        val previousPage = {}
        val page = Page(
            currentPageNumber = 1,
            nextPageNumber = null,
            previousPageNumber = null,
            results = mockk<ImmutableList<Character>>(relaxed = true)
        )

        // When
        val homePage = transformToHomePage(
            page = page,
            nextPage = nextPage,
            previousPage = previousPage
        )

        // Then
        assertNotEquals(homePage.nextPage, nextPage)
        assertNotEquals(homePage.previousPage, previousPage)
    }
}