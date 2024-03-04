package com.kimd13.characterprofilepresentation.view.species

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.characterprofilepresentation.R
import com.kimd13.characterprofilepresentation.view.shared.SectionLayout
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.design.component.StarWarsCard
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.speciescore.model.Language
import com.kimd13.speciescore.model.Language.Recorded
import com.kimd13.speciescore.model.Language.Unknown
import kotlinx.collections.immutable.persistentListOf

internal fun LazyListScope.speciesSection(
    state: CharacterProfileSectionState<SpeciesSection>
) = item {
    SectionLayout(
        state = state,
        title = stringResource(id = R.string.species_section_title)
    ) {
        SpeciesSection(it)
    }
}

@Composable
internal fun SpeciesSection(
    section: SpeciesSection,
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
                SpeciesInfoCard(info)
            }
        }
    }
}

@Composable
internal fun SpeciesInfoCard(
    info: SpeciesInfo,
    modifier: Modifier = Modifier
) {
    val contentSpacing = StarWarsTheme.spaces.small

    StarWarsCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing)
        ) {
            StarWarsText(stringResource(id = R.string.species_name, info.name))
            StarWarsText(info.language.toStringComposed())
        }
    }
}

@Composable
private fun Language.toStringComposed(): String {
    val languageValue = when (this) {
        is Recorded -> record
        Unknown -> stringResource(id = R.string.unknown_value)
    }

    return stringResource(id = R.string.species_language, languageValue)
}

@Preview
@Composable
private fun SpeciesSectionPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            SpeciesSection(
                section = SpeciesSection(
                    persistentListOf(
                        SpeciesInfo(
                            id = 1L,
                            name = "Human",
                            language = Unknown
                        ),
                        SpeciesInfo(
                            id = 2L,
                            name = "Dog",
                            language = Unknown
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun SpeciesSectionDarkPreview() {
    SpeciesSectionPreview(DARK)
}