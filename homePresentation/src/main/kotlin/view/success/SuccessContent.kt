package com.kimd13.homepresentation.view.success

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.VagueDescription
import com.kimd13.homepresentation.viewmodel.model.HomePage
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SuccessContent(
    page: HomePage,
    onCharacterClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val isAtBottom = listState.canScrollForward.not()
    val contentSpacing = StarWarsTheme.spaces.medium
    val lastItemSize = StarWarsTheme.sizes.medium

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            modifier = Modifier.fillMaxSize()
        ) {
            items(page.characters, key = { it.id }) {
                QuickGlanceCharacterCard(
                    name = it.name,
                    description = it.description,
                    onClick = { onCharacterClick(it.id) }
                )
            }
            // End item spacer is needed to ensure page controller
            // is clearly visible. Use string typing key to id this item, ensuring uniqueness.
            item(key = "") {
                Spacer(
                    modifier = Modifier.height(lastItemSize)
                )
            }
        }
        AnimatedVisibility(
            visible = isAtBottom,
            enter = fadeIn() + slideInVertically { it / 2 }, // Slides in from the bottom.
            exit = slideOutVertically { it / 2 } + fadeOut(), // Slides out from the bottom.
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            PageController(
                currentPageNumber = page.number,
                nextPageExists = page.nextPageExists,
                nextPage = page.nextPage,
                previousPageExists = page.previousPageExists,
                previousPage = page.previousPage
            )
        }
    }
}

@Preview
@Composable
private fun SuccessContentPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            SuccessContent(
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
                ),
                onCharacterClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun SuccessContentDarkPreview() {
    SuccessContentPreview(DARK)
}