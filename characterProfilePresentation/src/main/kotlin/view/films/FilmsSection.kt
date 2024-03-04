package com.kimd13.characterprofilepresentation.view.films

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.characterprofilepresentation.R
import com.kimd13.characterprofilepresentation.view.shared.SectionLayout
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.design.component.StarWarsExpandableCard
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import kotlinx.collections.immutable.persistentListOf

internal fun LazyListScope.filmsSection(
    state: CharacterProfileSectionState<FilmsSection>
) = item {
    SectionLayout(
        state = state,
        title = stringResource(id = R.string.films_section_title)
    ) {
        FilmsSection(
            section = it
        )
    }
}

@Composable
internal fun FilmsSection(
    section: FilmsSection,
    modifier: Modifier = Modifier
) {
    val contentSpacing = StarWarsTheme.spaces.medium

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(contentSpacing)
    ) {
        section.infoListing.forEach { info ->
            // To optimize performance key each item.
            // LazyColumn impl is not possible due to nested scrolling.
            key(info.id) {
                FilmInfoCard(info)
            }
        }
    }
}

@Composable
internal fun FilmInfoCard(
    info: FilmInfo,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val openingCrawlLines = if (expanded) {
        Int.MAX_VALUE
    } else {
        4
    }
    val contentSpacing = StarWarsTheme.spaces.small

    StarWarsExpandableCard(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onClick = { expanded = expanded.not() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            modifier = Modifier.animateContentSize()
        ) {
            StarWarsText(text = stringResource(id = R.string.films_title, info.title))
            StarWarsText(
                text = stringResource(id = R.string.film_opening_crawl, info.openingCrawl),
                maxLines = openingCrawlLines,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun FilmsSectionPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            FilmsSection(
                section = FilmsSection(
                    persistentListOf(
                        FilmInfo(
                            id = 1L,
                            title = "Working on Sample App",
                            openingCrawl = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
                        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                        nisi ut aliquip ex ea commodo consequat.
                    """.trimIndent()
                        ),
                        FilmInfo(
                            id = 2L,
                            title = "Working on Sample App Part 2",
                            openingCrawl = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
                        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                        nisi ut aliquip ex ea commodo consequat.
                    """.trimIndent()
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun FilmsSectionDarkPreview() {
    FilmsSectionPreview(DARK)
}