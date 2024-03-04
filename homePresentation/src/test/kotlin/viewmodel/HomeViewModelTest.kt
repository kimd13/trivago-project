package com.kimd13.homepresentation.viewmodel

import app.cash.turbine.test
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit
import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.core.Page
import com.kimd13.core.Response
import com.kimd13.design.component.StarWarsWarning
import com.kimd13.design.component.StarWarsWarning.*
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription
import com.kimd13.homepresentation.viewmodel.model.HomePage
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.Loading
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.ReadyForSearch
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @MockK
    lateinit var characterRepository: CharacterRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchCharacters expect loading state when fetch is loading`() = runTest {
        // Given
        val viewModel = HomeViewModel(characterRepository)
        val query = "Foo"
        val page = 1
        every {
            characterRepository.searchCharacters(query, page)
        } returns flowOf(Response.Loading)

        // When
        viewModel.searchCharacters(query)
        viewModel.screenState.test {

            // Then
            assertTrue(awaitItem() is ReadyForSearch)
            assertTrue(awaitItem() is Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchCharacters expect no results state when fetch has failed`() = runTest {
        // Given
        val viewModel = HomeViewModel(characterRepository)
        val query = "Foo"
        val page = 1
        every {
            characterRepository.searchCharacters(query, page)
        } returns flowOf(Response.Failure(IOException()))

        // When
        viewModel.searchCharacters(query)
        viewModel.screenState.test {

            // Then
            assertTrue(awaitItem() is ReadyForSearch)
            assertEquals(
                HomeScreenState.NoResults(
                    query = query,
                    warning = FAILURE
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchCharacters expect no results state when fetch returns empty results`() = runTest {
        // Given
        val viewModel = HomeViewModel(characterRepository)
        val query = "Foo"
        val page = 1
        every {
            characterRepository.searchCharacters(query, page)
        } returns flowOf(
            Response.Success(
                Page(
                    currentPageNumber = 1,
                    nextPageNumber = null,
                    previousPageNumber = null,
                    results = persistentListOf()
                )
            )
        )

        // When
        viewModel.searchCharacters(query)
        viewModel.screenState.test {

            // Then
            assertTrue(awaitItem() is ReadyForSearch)
            assertEquals(
                HomeScreenState.NoResults(
                    query = query,
                    warning = null
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchCharacters expect success state when fetch returns results`() = runTest {
        // Given
        val viewModel = HomeViewModel(characterRepository)
        val query = "Foo"
        val page = 1
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Recorded(172, FeetInches(5, 8))
        val birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY)
        val currentPageNumber = 2
        val nextPageNumber = 3
        val previousPageNumber = 1
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
        every {
            characterRepository.searchCharacters(query, page)
        } returns flowOf(
            Response.Success(
                Page(
                    currentPageNumber = currentPageNumber,
                    nextPageNumber = nextPageNumber,
                    previousPageNumber = previousPageNumber,
                    results = results
                )
            )
        )

        // When
        viewModel.searchCharacters(query)
        viewModel.screenState.test {

            // Then
            val expectation = HomePage(
                number = currentPageNumber,
                nextPageExists = true,
                nextPage = viewModel::nextPage,
                previousPageExists = true,
                previousPage = viewModel::previousPage,
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

            assertTrue(awaitItem() is ReadyForSearch)
            assertEquals(
                HomeScreenState.Success(
                    query = query,
                    page = expectation
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}