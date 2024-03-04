package com.kimd13.characterprofilepresentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.characterprofilepresentation.navigation.characterProfileId
import com.kimd13.characterprofilepresentation.viewmodel.mapper.characterPersonal.transformToCharacterPersonalSection
import com.kimd13.characterprofilepresentation.viewmodel.mapper.films.transformToFilmsSection
import com.kimd13.characterprofilepresentation.viewmodel.mapper.homeWorld.transformToHomeWorldSection
import com.kimd13.characterprofilepresentation.viewmodel.mapper.species.transformToSpeciesSection
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.core.Response
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.ResponseWithData
import com.kimd13.core.Response.ResponseWithoutData
import com.kimd13.core.Response.Success
import com.kimd13.design.component.StarWarsWarning
import com.kimd13.design.component.StarWarsWarning.DATA_POTENTIALLY_OUT_OF_SYNC
import com.kimd13.design.component.StarWarsWarning.FAILURE
import com.kimd13.filmcore.model.Film
import com.kimd13.filmcore.repository.FilmRepository
import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.repository.PlanetRepository
import com.kimd13.speciescore.model.Species
import com.kimd13.speciescore.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CharacterProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    characterRepository: CharacterRepository,
    private val speciesRepository: SpeciesRepository,
    private val planetRepository: PlanetRepository,
    private val filmRepository: FilmRepository
) : ViewModel() {

    private val id: Long = savedStateHandle.characterProfileId() // Navigation argument.

    private val characterResponse: StateFlow<Response<Character>> =
        characterRepository.fetchCharacter(id)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                Loading
            )

    val characterPersonalSectionState: StateFlow<CharacterProfileSectionState<CharacterPersonalSection>> =
        characterResponse
            .mapLatest(::transformToPersonalSectionState)
            .toSectionStateFlow()

    private fun transformToPersonalSectionState(
        response: Response<Character>
    ): CharacterProfileSectionState<CharacterPersonalSection> = transformToSectionState(
        response = response,
        dataTransform = ::transformToCharacterPersonalSection
    )

    private val speciesAbsentCharacterDataSectionState: Flow<CharacterProfileSectionState<SpeciesSection>> =
        characterResponse
            .filterAndMapCharacterDataAbsent()
            .mapLatest(::transformToDataAbsentSectionState)

    private val speciesPresentCharacterDataSectionState: Flow<CharacterProfileSectionState<SpeciesSection>> =
        characterResponse
            .filterAndMapCharacterDataPresent()
            .flatMapLatest { speciesRepository.fetchSpecies(it.data.speciesIds) }
            .mapLatest(::transformToSpeciesSectionState)

    private fun transformToSpeciesSectionState(
        response: Response<ImmutableList<Species>>
    ): CharacterProfileSectionState<SpeciesSection> = transformToSectionState(
        response = response,
        dataNothingToDisplayCheck = { it.isEmpty() },
        dataTransform = ::transformToSpeciesSection
    )

    val speciesSectionState: StateFlow<CharacterProfileSectionState<SpeciesSection>> =
        merge(speciesAbsentCharacterDataSectionState, speciesPresentCharacterDataSectionState)
            .toSectionStateFlow()

    private val homeWorldAbsentCharacterDataSectionState: Flow<CharacterProfileSectionState<HomeWorldSection>> =
        characterResponse
            .filterAndMapCharacterDataAbsent()
            .mapLatest(::transformToDataAbsentSectionState)

    private val homeWorldPresentCharacterDataSectionState: Flow<CharacterProfileSectionState<HomeWorldSection>> =
        characterResponse
            .filterAndMapCharacterDataPresent()
            .flatMapLatest { planetRepository.fetchPlanet(it.data.homeWorldId) }
            .mapLatest(::transformToHomeWorldSectionState)

    private fun transformToHomeWorldSectionState(
        response: Response<Planet>
    ): CharacterProfileSectionState<HomeWorldSection> = transformToSectionState(
        response = response,
        dataTransform = ::transformToHomeWorldSection
    )

    val homeWorldSectionState: StateFlow<CharacterProfileSectionState<HomeWorldSection>> =
        merge(homeWorldAbsentCharacterDataSectionState, homeWorldPresentCharacterDataSectionState)
            .toSectionStateFlow()

    private val filmsAbsentCharacterDataSectionState: Flow<CharacterProfileSectionState<FilmsSection>> =
        characterResponse
            .filterAndMapCharacterDataAbsent()
            .mapLatest(::transformToDataAbsentSectionState)

    private val filmsPresentCharacterDataSectionState: Flow<CharacterProfileSectionState<FilmsSection>> =
        characterResponse
            .filterAndMapCharacterDataPresent()
            .flatMapLatest { filmRepository.fetchFilms(it.data.filmIds) }
            .mapLatest(::transformToFilmsSectionState)

    private fun transformToFilmsSectionState(
        response: Response<ImmutableList<Film>>
    ): CharacterProfileSectionState<FilmsSection> = transformToSectionState(
        response = response,
        dataNothingToDisplayCheck = { it.isEmpty() },
        dataTransform = ::transformToFilmsSection
    )

    val filmsSectionState: StateFlow<CharacterProfileSectionState<FilmsSection>> =
        merge(filmsAbsentCharacterDataSectionState, filmsPresentCharacterDataSectionState)
            .toSectionStateFlow()

    private fun Flow<Response<Character>>.filterAndMapCharacterDataPresent() =
        filter { it is ResponseWithData }.mapLatest { it as ResponseWithData }

    private fun Flow<Response<Character>>.filterAndMapCharacterDataAbsent() =
        filter { it is ResponseWithoutData }.mapLatest { it as ResponseWithoutData }

    /**
     * Transforms a [Response] into a [CharacterProfileSectionState].
     * [Response]s are differentiated into [ResponseWithData] and [ResponseWithoutData].
     * Handled accordingly by [transformToDataPresentSectionState] or [transformToDataAbsentSectionState].
     */
    private fun <T : Any, R : Any> transformToSectionState(
        response: Response<T>,
        dataTransform: (T) -> R,
        dataNothingToDisplayCheck: (T) -> Boolean = { false }
    ): CharacterProfileSectionState<R> = when (response) {
        is ResponseWithoutData -> transformToDataAbsentSectionState(response)
        is ResponseWithData -> transformToDataPresentSectionState(
            response,
            dataTransform,
            getWarning(response),
            dataNothingToDisplayCheck
        )
    }

    /**
     * Decides what warning to produce given a [ResponseWithData].
     */
    private fun <T> getWarning(
        responseWithData: ResponseWithData<T>
    ): StarWarsWarning? = when (responseWithData) {
        is Success -> null
        is Backup -> DATA_POTENTIALLY_OUT_OF_SYNC
    }

    /**
     * Transforms a [ResponseWithData] into a [CharacterProfileSectionState].
     * [T] is a domain type that transforms into
     * [R] which is UI model for this presentation layer.
     */
    private fun <T : Any, R : Any> transformToDataPresentSectionState(
        response: ResponseWithData<T>,
        dataTransform: (T) -> R,
        warning: StarWarsWarning?,
        dataNothingToDisplayCheck: (T) -> Boolean = { false }
    ): CharacterProfileSectionState<R> =
        if (dataNothingToDisplayCheck(response.data)) {
            CharacterProfileSectionState.NothingToDisplay(
                warning = warning
            )
        } else {
            CharacterProfileSectionState.Success(
                section = dataTransform(response.data),
                warning = warning
            )
        }

    /**
     * Transforms a [ResponseWithoutData] into a [CharacterProfileSectionState].
     */
    private fun transformToDataAbsentSectionState(
        response: ResponseWithoutData
    ): CharacterProfileSectionState<Nothing> {
        return when (response) {
            is Failure -> CharacterProfileSectionState.NothingToDisplay(warning = FAILURE)
            Loading -> CharacterProfileSectionState.Loading
        }
    }

    /**
     * [stateIn] operation that all sections share.
     * Scoped to [viewModelScope], active while view is visible (std timeout of 5s) and
     * starts off with loading state.
     */
    private fun <T> Flow<CharacterProfileSectionState<T>>.toSectionStateFlow(): StateFlow<CharacterProfileSectionState<T>> =
        stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CharacterProfileSectionState.Loading
        )
}