package com.kimd13.characterprofilepresentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.characterprofilepresentation.navigation.CHARACTER_PROFILE_ID_ARG
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Loading
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.NothingToDisplay
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Success
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.core.Response
import com.kimd13.design.component.StarWarsWarning.DATA_POTENTIALLY_OUT_OF_SYNC
import com.kimd13.design.component.StarWarsWarning.FAILURE
import com.kimd13.filmcore.model.Film
import com.kimd13.filmcore.repository.FilmRepository
import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.model.Population
import com.kimd13.planetcore.repository.PlanetRepository
import com.kimd13.speciescore.model.Language
import com.kimd13.speciescore.model.Species
import com.kimd13.speciescore.repository.SpeciesRepository
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
class CharacterProfileViewModelTest {

    @MockK
    lateinit var characterRepository: CharacterRepository

    @MockK
    lateinit var speciesRepository: SpeciesRepository

    @MockK
    lateinit var planetRepository: PlanetRepository

    @MockK
    lateinit var filmRepository: FilmRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    /**
     * Check that all mappings between models below
     * match expectations. Testing focuses on state
     * emissions and assumes mappings were already tested.
     */
    private val id = 1L
    private val savedStateHandle = SavedStateHandle(
        mapOf(Pair(CHARACTER_PROFILE_ID_ARG, id))
    )
    private val name = "Luke Skywalker"
    private val height = Height.Unknown
    private val birthYear = BirthYear.Unknown
    private val homeWorldId = 1L
    private val speciesId = 1L
    private val speciesIds: ImmutableList<Long> = persistentListOf(speciesId)
    private val filmId = 1L
    private val filmsIds: ImmutableList<Long> = persistentListOf(filmId)
    private val character = Character(
        id = id,
        name = name,
        height = height,
        birthYear = birthYear,
        imageUrl = null,
        homeWorldId = homeWorldId,
        speciesIds = speciesIds,
        filmIds = filmsIds
    )
    private val characterPersonalSection = CharacterPersonalSection(
        CharacterPersonalInfo(
            name, birthYear, height
        )
    )

    private val planetName = "Earth"
    private val planetPopulation = Population.Unknown
    private val planet = Planet(
        id = homeWorldId,
        name = planetName,
        population = planetPopulation
    )
    private val homeWorldSection = HomeWorldSection(
        HomeWorldInfo(
            name = planetName,
            population = planetPopulation
        )
    )

    private val speciesName = "Human"
    private val speciesLanguage = Language.Unknown
    private val species = Species(
        id = speciesId,
        name = speciesName,
        language = speciesLanguage
    )
    private val speciesSection = SpeciesSection(
        persistentListOf(
            SpeciesInfo(
                id = speciesId,
                name = speciesName,
                language = speciesLanguage
            )
        )
    )

    private val filmTitle = "Episode 1"
    private val filmOpeningCrawl = "A long, long, time ago..."
    private val film = Film(
        id = filmId,
        title = filmTitle,
        openingCrawl = filmOpeningCrawl
    )
    private val filmSection = FilmsSection(
        persistentListOf(
            FilmInfo(
                id = filmId,
                title = filmTitle,
                openingCrawl = filmOpeningCrawl
            )
        )
    )

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
    fun `characterPersonalSectionState expect loading state when fetch is loading`() = runTest {
        // Given
        every { characterRepository.fetchCharacter(id) } returns
                flowOf(Response.Loading)
        val viewModel = initializeAndGetViewModel()

        // When
        viewModel.characterPersonalSectionState.test {

            // Then
            assertTrue(awaitItem() is Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `characterPersonalSectionState expect nothing to display with warning state when fetch fails`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Failure(IOException()))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.characterPersonalSectionState.test {

                // Then
                assertEquals(
                    NothingToDisplay(
                        warning = FAILURE
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `characterPersonalSectionState expect success with warning state when fetch is back up`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(
                        Response.Backup(
                            exception = IOException(),
                            data = character
                        )
                    )
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.characterPersonalSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = characterPersonalSection,
                        warning = DATA_POTENTIALLY_OUT_OF_SYNC
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `characterPersonalSectionState expect success when fetch is successful`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.characterPersonalSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = characterPersonalSection,
                        warning = null
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `speciesSectionState expect loading state when character fetch is loading`() = runTest {
        // Given
        every { characterRepository.fetchCharacter(id) } returns
                flowOf(Response.Loading)
        val viewModel = initializeAndGetViewModel()

        // When
        viewModel.speciesSectionState.test {

            // Then
            assertTrue(awaitItem() is Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `speciesSectionState expect nothing to display with warning state when character fetch fails`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Failure(IOException()))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.speciesSectionState.test {

                // Then
                assertEquals(
                    NothingToDisplay(
                        warning = FAILURE
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `speciesSectionState expect loading state when species fetch is loading`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { speciesRepository.fetchSpecies(speciesIds) } returns
                    flowOf(Response.Loading)
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.speciesSectionState.test {

                // Then
                assertTrue(awaitItem() is Loading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `speciesSectionState expect success state with warning when species fetch is back up`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { speciesRepository.fetchSpecies(speciesIds) } returns
                    flowOf(
                        Response.Backup(
                            exception = IOException(),
                            data = persistentListOf(species)
                        )
                    )
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.speciesSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = speciesSection,
                        warning = DATA_POTENTIALLY_OUT_OF_SYNC
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `speciesSectionState expect success state when species fetch is successful`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { speciesRepository.fetchSpecies(speciesIds) } returns
                    flowOf(Response.Success(persistentListOf(species)))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.speciesSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = speciesSection,
                        warning = null
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeWorldSectionState expect loading state when character fetch is loading`() = runTest {
        // Given
        every { characterRepository.fetchCharacter(id) } returns
                flowOf(Response.Loading)
        val viewModel = initializeAndGetViewModel()

        // When
        viewModel.homeWorldSectionState.test {

            // Then
            assertTrue(awaitItem() is Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `homeWorldSectionState expect nothing to display with warning state when character fetch fails`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Failure(IOException()))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.homeWorldSectionState.test {

                // Then
                assertEquals(
                    NothingToDisplay(
                        warning = FAILURE
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeWorldSectionState expect loading state when planet fetch is loading`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { planetRepository.fetchPlanet(homeWorldId) } returns
                    flowOf(Response.Loading)
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.homeWorldSectionState.test {

                // Then
                assertTrue(awaitItem() is Loading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeWorldSectionState expect success state with warning when planet fetch is back up`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { planetRepository.fetchPlanet(homeWorldId) } returns
                    flowOf(
                        Response.Backup(
                            exception = IOException(),
                            data = planet
                        )
                    )
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.homeWorldSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = homeWorldSection,
                        warning = DATA_POTENTIALLY_OUT_OF_SYNC
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeWorldSectionState expect success state when planet fetch is successful`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { planetRepository.fetchPlanet(homeWorldId) } returns
                    flowOf(Response.Success(planet))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.homeWorldSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = homeWorldSection,
                        warning = null
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `filmsSectionState expect loading state when character fetch is loading`() = runTest {
        // Given
        every { characterRepository.fetchCharacter(id) } returns
                flowOf(Response.Loading)
        val viewModel = initializeAndGetViewModel()

        // When
        viewModel.filmsSectionState.test {

            // Then
            assertTrue(awaitItem() is Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filmsSectionState expect nothing to display with warning state when character fetch fails`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Failure(IOException()))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.filmsSectionState.test {

                // Then
                assertEquals(
                    NothingToDisplay(
                        warning = FAILURE
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `filmsSectionState expect loading state when films fetch is loading`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { filmRepository.fetchFilms(filmsIds) } returns
                    flowOf(Response.Loading)
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.filmsSectionState.test {

                // Then
                assertTrue(awaitItem() is Loading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `filmsSectionState expect success state with warning when films fetch is back up`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { filmRepository.fetchFilms(filmsIds) } returns
                    flowOf(
                        Response.Backup(
                            exception = IOException(),
                            data = persistentListOf(film)
                        )
                    )
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.filmsSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = filmSection,
                        warning = DATA_POTENTIALLY_OUT_OF_SYNC
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `filmsSectionState expect success state when films fetch is successful`() =
        runTest {
            // Given
            every { characterRepository.fetchCharacter(id) } returns
                    flowOf(Response.Success(character))
            every { filmRepository.fetchFilms(filmsIds) } returns
                    flowOf(Response.Success(persistentListOf(film)))
            val viewModel = initializeAndGetViewModel()

            // When
            viewModel.filmsSectionState.test {

                // Then
                assertEquals(
                    Success(
                        section = filmSection,
                        warning = null
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun initializeAndGetViewModel() = CharacterProfileViewModel(
        savedStateHandle = savedStateHandle,
        characterRepository = characterRepository,
        speciesRepository = speciesRepository,
        planetRepository = planetRepository,
        filmRepository = filmRepository
    )
}