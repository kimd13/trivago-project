package com.kimd13.characterprofilepresentation.view.characterPersonal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Height
import com.kimd13.characterprofilepresentation.R
import com.kimd13.characterprofilepresentation.view.shared.SectionLayout
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.design.component.StarWarsCard
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

internal fun LazyListScope.characterPersonalSection(
    state: CharacterProfileSectionState<CharacterPersonalSection>
) = item {
    SectionLayout(
        state = state,
        title = stringResource(id = R.string.character_personal_section_title)
    ) {
        CharacterPersonalSection(it)
    }
}

@Composable
internal fun CharacterPersonalSection(
    section: CharacterPersonalSection,
    modifier: Modifier = Modifier
) {
    val contentSpacing = StarWarsTheme.spaces.small

    StarWarsCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing)
        ) {
            with(section.info) {
                StarWarsText(stringResource(id = R.string.character_personal_name, name))
                StarWarsText(birthYear.toStringComposed())
                StarWarsText(height.toStringComposed())
            }
        }
    }
}

@Composable
private fun BirthYear.toStringComposed(): String {
    val birthYearValue = when (this) {
        is BirthYear.Recorded -> toString()
        BirthYear.Unknown -> stringResource(id = R.string.unknown_value)
    }

    return stringResource(
        id = R.string.character_personal_birth_year,
        birthYearValue
    )
}

@Composable
private fun Height.toStringComposed(): String {
    val heightValue = when (this) {
        // Units of measurement must be translated so
        // we have to do another pass.
        is Height.Recorded -> stringResource(
            id = R.string.character_personal_height_value,
            inFeetInches.feet,
            inFeetInches.inches,
            inCentimeters
        )

        Height.Unknown -> stringResource(id = R.string.unknown_value)
    }

    return stringResource(id = R.string.character_personal_height, heightValue)
}

@Preview
@Composable
private fun CharacterPersonalSectionPreview(
    themeContent: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContent) {
        StarWarsSurface {
            CharacterPersonalSection(
                section = CharacterPersonalSection(
                    CharacterPersonalInfo(
                        name = "Daniel Kim",
                        birthYear = BirthYear.Unknown,
                        height = Height.Unknown
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun CharacterPersonalSectionDarkPreview() {
    CharacterPersonalSectionPreview(DARK)
}