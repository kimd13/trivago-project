package com.kimd13.design.theme.elevation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

data class StarWarsElevations internal constructor(
    val slightlyRaised: Dp
)

internal fun starWarsElevations(
    themeContext: StarWarsThemeContext
): StarWarsElevations = when (themeContext) {
    LIGHT -> starWarsLightElevations()
    DARK -> starWarsDarkElevations()
}

private fun starWarsLightElevations(): StarWarsElevations = StarWarsElevations(
    slightlyRaised = 6.dp
)

/**
 * Elevations in dark surfaces need to be more pronounced.
 */
private fun starWarsDarkElevations(): StarWarsElevations = StarWarsElevations(
    slightlyRaised = 8.dp
)