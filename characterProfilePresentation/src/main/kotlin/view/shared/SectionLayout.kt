package com.kimd13.characterprofilepresentation.view.shared

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Height
import com.kimd13.characterprofilepresentation.view.characterPersonal.CharacterPersonalSection
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.CharacterProfileSectionStateWithWarning
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Loading
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.NothingToDisplay
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Success
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.core.mapImmutable
import com.kimd13.design.component.StarWarsWarning
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Provides and arranges reused section components like
 * warning badge, nothing to display and loading contents.
 */
@Composable
internal fun <T> SectionLayout(
    state: CharacterProfileSectionState<T>,
    title: String,
    successContent: @Composable (T) -> Unit
) {
    val warning = if (state is CharacterProfileSectionStateWithWarning) {
        state.warning
    } else {
        null
    }
    val contentSpacing = StarWarsTheme.spaces.small

    Column(
        verticalArrangement = Arrangement.spacedBy(contentSpacing),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        SectionHeading(
            text = title,
            warning = warning
        )
        Crossfade(
            targetState = state,
            label = "SectionContentCrossfade"
        ) {
            when (it) {
                Loading -> SectionLoadingContent()
                is NothingToDisplay -> SectionNothingToDisplayContent()
                is Success -> successContent(it.section)
            }
        }
    }
}

@Preview
@Composable
private fun SectionLayoutPreview(
    themeContext: StarWarsThemeContext = LIGHT,
    states: ImmutableList<CharacterProfileSectionState<CharacterPersonalSection>> =
        StarWarsWarning.entries.mapImmutable {
            Success(
                section = CharacterPersonalSection(
                    CharacterPersonalInfo(
                        name = "Daniel Kim",
                        birthYear = BirthYear.Unknown,
                        height = Height.Unknown
                    )
                ),
                warning = it
            )
        }
) {
    StarWarsTheme(themeContext) {
        Column(
            modifier = Modifier.background(StarWarsTheme.colors.surface)
        ) {
            states.forEach { state ->
                SectionLayout(
                    state = state,
                    title = "Preview"
                ) {
                    CharacterPersonalSection(it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SectionLayoutDarkPreview() {
    SectionLayoutPreview(DARK)
}

@Preview
@Composable
private fun SectionLayoutLoadingPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    SectionLayoutPreview(
        states = persistentListOf(Loading),
        themeContext = themeContext
    )
}

@Preview
@Composable
private fun SectionLayoutLoadingDarkPreview() {
    SectionLayoutLoadingPreview(DARK)
}

@Preview
@Composable
private fun SectionLayoutNothingToDisplayPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    SectionLayoutPreview(
        states = StarWarsWarning.entries.mapImmutable {
            NothingToDisplay(it)
        },
        themeContext = themeContext
    )
}

@Preview
@Composable
private fun SectionLayoutNothingToDisplayDarkPreview() {
    SectionLayoutNothingToDisplayPreview(DARK)
}