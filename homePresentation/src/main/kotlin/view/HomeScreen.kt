package com.kimd13.homepresentation.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsSearchBar
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.homepresentation.view.loading.LoadingContent
import com.kimd13.homepresentation.view.noResults.NoResultsContent
import com.kimd13.homepresentation.view.success.SuccessContent
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.VagueDescription
import com.kimd13.homepresentation.viewmodel.model.HomePage
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.Loading
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.NoResults
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.ReadyForSearch
import com.kimd13.homepresentation.viewmodel.model.HomeScreenState.Success
import com.kimd13.homepresentation.viewmodel.model.HomeSearchArgs
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun HomeScreen(
    state: HomeScreenState,
    searchArgs: HomeSearchArgs,
    searchCharacters: (query: String) -> Unit,
    onCharacterClick: (id: Long) -> Unit
) {
    val backgroundColor = StarWarsTheme.colors.surface
    val loading = state is Loading
    val searchBarPadding = StarWarsTheme.spaces.large
    val horizontalContentPadding = StarWarsTheme.spaces.large

    ExpandingScreen(
        backgroundColor = backgroundColor
    ) {
        StarWarsSearchBar(
            query = searchArgs.query,
            onQueryChange = searchCharacters,
            loading = loading,
            modifier = Modifier
                .padding(searchBarPadding)
                .fillMaxWidth()
        )

        Crossfade(
            targetState = state,
            label = "HomeScreenContentCrossfade",
            modifier = Modifier.padding(
                horizontal = horizontalContentPadding
            )
        ) {
            when (it) {
                ReadyForSearch -> {}

                is Loading -> LoadingContent(
                    query = it.query
                )

                is NoResults -> NoResultsContent(
                    query = it.query,
                    warning = it.warning
                )

                is Success -> SuccessContent(
                    page = it.page,
                    onCharacterClick = onCharacterClick
                )
            }
        }
    }
}

/**
 * Nested layout that places content in the center
 * and expands (with animation) out as new content is
 * place within it.
 */
@Composable
private fun ExpandingScreen(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val contentExpansionDurationMillis = 1_000

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize(tween(contentExpansionDurationMillis)),
            content = content
        )
    }
}


@Preview
@Composable
private fun HomeScreenPreview(
    themeContext: StarWarsThemeContext = LIGHT,
    state: HomeScreenState = Success(
        query = "Foo",
        page = HomePage(
            number = 1,
            nextPageExists = true,
            nextPage = {},
            previousPageExists = true,
            previousPage = {},
            characters = persistentListOf(
                HomeCharacter(0L, "Anakin Skywalker", VagueDescription),
                HomeCharacter(1L, "Yoda", VagueDescription),
                HomeCharacter(2L, "Obi-Wan Kenobi", VagueDescription),
                HomeCharacter(3L, "Han Solo", VagueDescription)
            )
        )
    )
) {
    StarWarsTheme(themeContext) {
        HomeScreen(
            state = state,
            searchArgs = HomeSearchArgs(
                query = "Foo",
                page = 1
            ),
            searchCharacters = {},
            onCharacterClick = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenDarkPreview() {
    HomeScreenPreview(DARK)
}

@Preview
@Composable
private fun HomeScreenReadyForSearchPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    HomeScreenPreview(
        state = ReadyForSearch,
        themeContext = themeContext
    )
}

@Preview
@Composable
private fun HomeScreenReadyForSearchDarkPreview() {
    HomeScreenReadyForSearchPreview(DARK)
}

/**
 * More detailed @Previews about each state available
 * at NoResultsContent.kt
 */
@Preview
@Composable
private fun HomeScreenNoResultsPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    HomeScreenPreview(
        state = NoResults("Foo"),
        themeContext = themeContext
    )
}

@Preview
@Composable
private fun HomeScreenNoResultsDarkPreview() {
    HomeScreenNoResultsPreview(DARK)
}

@Preview
@Composable
private fun HomeScreenLoadingPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    HomeScreenPreview(
        state = Loading("Foo"),
        themeContext = themeContext
    )
}

@Preview
@Composable
private fun HomeScreenLoadingDarkPreview() {
    HomeScreenLoadingPreview(DARK)
}