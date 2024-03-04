package com.kimd13.homepresentation.view.success

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsExpandableCard
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.component.StarWarsTextButton
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.homepresentation.R
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.BirthYearOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.CompleteDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.HeightOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.VagueDescription

@Composable
internal fun QuickGlanceCharacterCard(
    name: String,
    description: HomeCharacterDescription,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val contentSpacing = StarWarsTheme.spaces.small

    StarWarsExpandableCard(
        expanded = expanded,
        onClick = { expanded = expanded.not() },
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            modifier = Modifier.animateContentSize()
        ) {
            StarWarsText(name)
            if (expanded) {
                StarWarsText(description.toStringComposed())
                StarWarsTextButton(
                    text = stringResource(id = R.string.more_details),
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun HomeCharacterDescription.toStringComposed(): String = when (this) {
    is CompleteDescription -> stringResource(
        id = R.string.home_character_complete_description,
        birthYear.toString(),
        height.inFeetInches.feet,
        height.inFeetInches.inches,
        height.inCentimeters
    )

    is BirthYearOnlyDescription -> stringResource(
        id = R.string.home_character_birth_year_description,
        birthYear.toString()
    )

    is HeightOnlyDescription -> stringResource(
        id = R.string.home_character_height_description,
        height.inFeetInches.feet,
        height.inFeetInches.inches,
        height.inCentimeters
    )

    VagueDescription -> stringResource(id = R.string.home_character_vague_description)
}

@Preview
@Composable
private fun QuickGlanceCharacterCardPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            QuickGlanceCharacterCard(
                name = "Han Solo",
                description = VagueDescription,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun QuickGlanceCharacterCardDarkPreview() {
    QuickGlanceCharacterCardPreview(DARK)
}
