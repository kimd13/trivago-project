package com.kimd13.characterprofilepresentation.view.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.characterprofilepresentation.R
import com.kimd13.design.component.StarWarsSurface
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
internal fun SectionNothingToDisplayContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        StarWarsText(stringResource(id = R.string.nothing_to_display_message))
    }
}

@Preview
@Composable
private fun SectionNothingToDisplayContentPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsSurface {
            SectionNothingToDisplayContent()
        }
    }
}

@Preview
@Composable
private fun SectionNothingToDisplayContentDarkPreview() {
    SectionNothingToDisplayContentPreview(DARK)
}
