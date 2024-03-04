package com.kimd13.homepresentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.core.Page
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.design.component.StarWarsWarning.FAILURE
import com.kimd13.homepresentation.viewmodel.mapper.transformToHomePage
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState
import com.kimd13.homepresentation.viewmodel.model.HomeSearchArgs
import com.kimd13.homepresentation.viewmodel.model.PageResponseWithArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

// This a good standard where the value is not too long (avoid unresponsiveness)
// or too short (reduces backpressure).
private val SEARCH_DEBOUNCE = 300.milliseconds
private const val INITIAL_QUERY = ""
internal const val INITIAL_PAGE_NUMBER = 1

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _searchArgs: MutableStateFlow<HomeSearchArgs> = MutableStateFlow(
        HomeSearchArgs(INITIAL_QUERY, INITIAL_PAGE_NUMBER)
    )
    val searchArgs: StateFlow<HomeSearchArgs> = _searchArgs.asStateFlow()

    private val invalidSearchArgsScreenState: Flow<HomeScreenState> = searchArgs
        .debounce(SEARCH_DEBOUNCE)
        .filterNot(::isValidSearchArgs)
        .mapLatest(::transformToInvalidSearchArgsHomeScreenState)

    private fun transformToInvalidSearchArgsHomeScreenState(
        fromHomeSearchArgs: HomeSearchArgs
    ): HomeScreenState = if (fromHomeSearchArgs.query.isBlank()) {
        HomeScreenState.ReadyForSearch
    } else {
        // Should not be reachable since the validity check we perform is a blank query check.
        throw IllegalStateException("Unable to transform to HomeScreenState.")
    }

    private val validSearchArgsScreenState: Flow<HomeScreenState> = searchArgs
        .debounce(SEARCH_DEBOUNCE)
        .filter(::isValidSearchArgs)
        .flatMapLatest { searchCharacters(it) }
        .mapLatest(::transformToHomeScreenState)

    val screenState: StateFlow<HomeScreenState> =
        merge(invalidSearchArgsScreenState, validSearchArgsScreenState)
            .stateIn(
                viewModelScope,
                // Keep the flow since users back enter this screen often.
                SharingStarted.Eagerly,
                HomeScreenState.ReadyForSearch
            )

    private fun isValidSearchArgs(
        searchArgs: HomeSearchArgs
    ): Boolean = searchArgs.query.isNotBlank()

    /**
     * Wrapper around domain function search characters.
     * This is needed because UI models need information
     * about the search args used. Depending on the response
     * data like the query used is lost.
     */
    private fun searchCharacters(
        searchArgs: HomeSearchArgs
    ): Flow<PageResponseWithArgs> =
        characterRepository.searchCharacters(
            name = searchArgs.query,
            page = searchArgs.page
        ).mapLatest {
            PageResponseWithArgs(it, searchArgs)
        }

    private fun transformToHomeScreenState(
        pageResponse: PageResponseWithArgs
    ): HomeScreenState = with(pageResponse) {
        when (response) {
            is Failure -> HomeScreenState.NoResults(searchArgs.query, FAILURE)
            Loading -> HomeScreenState.Loading(searchArgs.query)
            is Success -> transformToSuccessHomeScreenState(searchArgs, response)
            // Should not be reachable as the data layer does not send this response.
            is Backup -> throw IllegalStateException("Implementation for backup data is not complete.")
        }
    }

    private fun transformToSuccessHomeScreenState(
        searchArgs: HomeSearchArgs,
        response: Success<Page<ImmutableList<Character>>>
    ): HomeScreenState = if (response.data.results.isNotEmpty()) {
        HomeScreenState.Success(
            query = searchArgs.query,
            page = transformToHomePage(
                page = response.data,
                nextPage = ::nextPage,
                previousPage = ::previousPage
            )
        )
    } else {
        HomeScreenState.NoResults(searchArgs.query)
    }

    fun searchCharacters(newQuery: String) {
        _searchArgs.value = HomeSearchArgs(
            query = newQuery,
            page = INITIAL_PAGE_NUMBER // Reset to initial page.
        )
    }

    internal fun nextPage() {
        val currentPage = _searchArgs.value.page
        _searchArgs.value = _searchArgs.value.copy(
            page = currentPage.plus(1)
        )
    }

    internal fun previousPage() {
        val currentPage = _searchArgs.value.page
        _searchArgs.value = _searchArgs.value.copy(
            page = currentPage.minus(1)
        )
    }
}