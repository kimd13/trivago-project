package com.kimd13.homepresentation.viewmodel.model

import androidx.compose.runtime.Immutable
import com.kimd13.design.component.StarWarsWarning

@Immutable
sealed interface HomeScreenState {

    data object ReadyForSearch : HomeScreenState

    data class Success(
        val query: String,
        val page: HomePage
    ) : HomeScreenState

    data class NoResults(
        val query: String,
        val warning: StarWarsWarning? = null
    ) : HomeScreenState

    data class Loading(
        val query: String
    ) : HomeScreenState
}