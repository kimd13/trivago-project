package com.kimd13.homepresentation.view.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsIconButton
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
internal fun PageController(
    currentPageNumber: Int,
    nextPageExists: Boolean,
    nextPage: () -> Unit,
    previousPageExists: Boolean,
    previousPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val previousIcon = StarWarsTheme.icons.arrowLeft
    val nextIcon = StarWarsTheme.icons.arrowRight

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        StarWarsIconButton(
            imageVector = previousIcon,
            enabled = previousPageExists,
            onClick = previousPage
        )
        StarWarsText("$currentPageNumber")
        StarWarsIconButton(
            imageVector = nextIcon,
            enabled = nextPageExists,
            onClick = nextPage
        )
    }
}

@Preview
@Composable
private fun PageControllerPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column(
            modifier = Modifier.background(StarWarsTheme.colors.surface)
        ) {
            PageController(
                currentPageNumber = 6,
                nextPageExists = true,
                nextPage = {},
                previousPageExists = true,
                previousPage = {}
            )
            PageController(
                currentPageNumber = 6,
                nextPageExists = false,
                nextPage = {},
                previousPageExists = true,
                previousPage = {}
            )
            PageController(
                currentPageNumber = 6,
                nextPageExists = true,
                nextPage = {},
                previousPageExists = false,
                previousPage = {}
            )
        }
    }
}

@Preview
@Composable
private fun PageControllerDarkPreview() {
    PageControllerPreview(DARK)
}