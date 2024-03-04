package com.kimd13.characterprofilepresentation.view.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.component.StarWarsWarning
import com.kimd13.design.component.StarWarsWarningBadge
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
internal fun SectionHeading(
    text: String,
    warning: StarWarsWarning?,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        StarWarsText(
            text = text,
            style = StarWarsTheme.typography.title
        )
        if (warning != null) {
            StarWarsWarningBadge(warning)
        }
    }
}

@Preview
@Composable
private fun SectionHeadingPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column(
            modifier = Modifier.background(StarWarsTheme.colors.surface)
        ) {
            StarWarsWarning.entries.forEach {
                SectionHeading(
                    text = "Section",
                    warning = it
                )
            }
        }
    }
}

@Preview
@Composable
private fun SectionHeadingDarkPreview() {
    SectionHeadingPreview(DARK)
}