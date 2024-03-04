package com.kimd13.characterprofilepresentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Height
import com.kimd13.characterprofilepresentation.view.characterPersonal.characterPersonalSection
import com.kimd13.characterprofilepresentation.view.films.filmsSection
import com.kimd13.characterprofilepresentation.view.homeWorld.homeWorldSection
import com.kimd13.characterprofilepresentation.view.species.speciesSection
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.planetcore.model.Population
import com.kimd13.speciescore.model.Language
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CharacterProfileScreen(
    characterPersonalSectionState: CharacterProfileSectionState<CharacterPersonalSection>,
    speciesSectionState: CharacterProfileSectionState<SpeciesSection>,
    homeWorldSectionState: CharacterProfileSectionState<HomeWorldSection>,
    filmsSectionState: CharacterProfileSectionState<FilmsSection>
) {
    val backgroundColor = StarWarsTheme.colors.surface
    val contentSpacing = StarWarsTheme.spaces.medium
    val padding = StarWarsTheme.spaces.large

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(contentSpacing),
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = padding)
    ) {
        verticalPadding(padding, contentSpacing)
        characterPersonalSection(
            state = characterPersonalSectionState
        )
        speciesSection(
            state = speciesSectionState
        )
        homeWorldSection(
            state = homeWorldSectionState
        )
        filmsSection(
            state = filmsSectionState
        )
        verticalPadding(padding, contentSpacing)
    }
}

/**
 * Better alternative to vertical padding on entire
 * screen since padding clips scrolling content.
 * [contentSpacing] needs to be accounted for and subtracted.
 */
private fun LazyListScope.verticalPadding(
    padding: Dp,
    contentSpacing: Dp
) = item {
    Spacer(modifier = Modifier.height(padding - contentSpacing))
}

/**
 * More detailed @Previews about each state available
 * at SectionLayout.kt
 */
@Preview
@Composable
private fun CharacterProfileScreenPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        CharacterProfileScreen(
            characterPersonalSectionState = CharacterProfileSectionState.Success(
                section = CharacterPersonalSection(
                    CharacterPersonalInfo(
                        name = "Daniel Kim",
                        birthYear = BirthYear.Unknown,
                        height = Height.Unknown
                    )
                )
            ),
            speciesSectionState = CharacterProfileSectionState.Success(
                section = SpeciesSection(
                    persistentListOf(
                        SpeciesInfo(
                            id = 1L,
                            name = "Human",
                            language = Language.Unknown
                        ),
                        SpeciesInfo(
                            id = 2L,
                            name = "Dog",
                            language = Language.Unknown
                        )
                    )
                )
            ),
            homeWorldSectionState = CharacterProfileSectionState.Success(
                section = HomeWorldSection(
                    HomeWorldInfo(
                        name = "Germany",
                        population = Population.Unknown
                    )
                )
            ),
            filmsSectionState = CharacterProfileSectionState.Success(
                section = FilmsSection(
                    persistentListOf(
                        FilmInfo(
                            id = 1L,
                            title = "Working on Sample App",
                            openingCrawl = "Long, long, long ago..."
                        ),
                        FilmInfo(
                            id = 1L,
                            title = "Working on Sample App Part 2",
                            openingCrawl = "Even longer ago..."
                        )
                    )
                )
            )
        )
    }
}

@Preview
@Composable
private fun CharacterProfileScreenDarkPreview() {
    CharacterProfileScreenPreview(DARK)
}