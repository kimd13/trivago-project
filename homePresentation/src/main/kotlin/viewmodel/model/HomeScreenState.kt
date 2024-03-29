package com.kimd13.homepresentation.viewmodel.model

import com.kimd13.design.component.StarWarsWarning

sealed class HomeScreenState {

    data object ReadyForSearch : HomeScreenState()

    data class Success(
        val query: String,
        val page: HomePage
    ) : HomeScreenState()

    data class NoResults(
        val query: String,
        val warning: StarWarsWarning? = null
    ) : HomeScreenState()

    data class Loading(
        val query: String
    ) : HomeScreenState()
}