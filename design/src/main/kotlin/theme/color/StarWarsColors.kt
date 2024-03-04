package com.kimd13.design.theme.color

import androidx.compose.ui.graphics.Color
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

data class StarWarsColors internal constructor(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceElevated: Color,
    val error: Color,
    val onError: Color,
    val highlight: Color,
    val onHighlight: Color
)

internal fun starWarsColors(
    themeContext: StarWarsThemeContext
): StarWarsColors = when (themeContext) {
    LIGHT -> starWarsLightColors()
    DARK -> starWarsDarkColors()
}

private fun starWarsLightColors(): StarWarsColors = StarWarsColors(
    primary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceElevated = Color.White,
    error = Color(0xFFd32f2f),
    onError = Color.White,
    highlight = Color(0xFFf57c00),
    onHighlight = Color.White
)

private fun starWarsDarkColors(): StarWarsColors = StarWarsColors(
    primary = Color.White,
    surface = Color(0xFF2D2D30),
    onSurface = Color.White,
    surfaceElevated = Color(0xFF3E3E42),
    error = Color(0xFFE57373),
    onError = Color.Black,
    highlight = Color(0xFFFFB74D),
    onHighlight = Color.Black
)