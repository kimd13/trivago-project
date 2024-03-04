package com.kimd13.characterprofilepresentation.view.homeWorld

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.characterprofilepresentation.R
import com.kimd13.characterprofilepresentation.view.shared.SectionLayout
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.design.component.StarWarsCard
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.planetcore.model.Population
import com.kimd13.planetcore.model.Population.Recorded
import com.kimd13.planetcore.model.Population.Unknown

internal fun LazyListScope.homeWorldSection(
    state: CharacterProfileSectionState<HomeWorldSection>
) = item {
    SectionLayout(
        state = state,
        title = stringResource(id = R.string.home_world_section_title)
    ) {
        HomeWorldSection(it)
    }
}

@Composable
internal fun HomeWorldSection(
    section: HomeWorldSection,
    modifier: Modifier = Modifier
) {
    val contentSpacing = StarWarsTheme.spaces.small

    with(section.info) {
        StarWarsCard(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(contentSpacing)
            ) {
                StarWarsText(stringResource(id = R.string.home_world_name, name))
                StarWarsText(population.toStringComposed())
            }
        }
    }
}

@Composable
private fun Population.toStringComposed(): String {
    val populationValue = when (this) {
        is Recorded -> record
        Unknown -> stringResource(id = R.string.unknown_value)
    }

    return stringResource(id = R.string.home_world_population, populationValue)
}

@Preview
@Composable
private fun HomeWorldSectionPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            HomeWorldSection(
                section = HomeWorldSection(
                    HomeWorldInfo(
                        name = "Germany",
                        population = Unknown
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun HomeWorldSectionDarkPreview() {
    HomeWorldSectionPreview(DARK)
}