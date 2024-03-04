package com.kimd13.homepresentation.view.noResults

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.StarWarsText
import com.kimd13.design.component.StarWarsWarning
import com.kimd13.design.component.StarWarsWarningBadge
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.homepresentation.R

@Composable
internal fun NoResultsContent(
    query: String,
    warning: StarWarsWarning?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        StarWarsText(stringResource(id = R.string.no_results_found, query))
        if (warning != null) {
            StarWarsWarningBadge(
                warning = warning,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Preview
@Composable
private fun NoResultsContentPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            StarWarsWarning.entries.plus(null).forEach {
                NoResultsContent(
                    query = "Foo",
                    warning = it,
                    modifier = Modifier.weight(1F)
                )
            }
        }
    }
}

@Preview
@Composable
private fun NoResultsContentDarkPreview() {
    NoResultsContentPreview(DARK)
}