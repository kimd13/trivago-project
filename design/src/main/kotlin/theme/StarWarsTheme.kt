package com.kimd13.design.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import com.kimd13.design.theme.border.StarWarsBorders
import com.kimd13.design.theme.border.starWarsBorders
import com.kimd13.design.theme.color.StarWarsColors
import com.kimd13.design.theme.color.starWarsColors
import com.kimd13.design.theme.elevation.StarWarsElevations
import com.kimd13.design.theme.elevation.starWarsElevations
import com.kimd13.design.theme.icon.StarWarsIcons
import com.kimd13.design.theme.icon.starWarsIcons
import com.kimd13.design.theme.shape.StarWarsShapes
import com.kimd13.design.theme.shape.starWarsShapes
import com.kimd13.design.theme.size.StarWarsSizes
import com.kimd13.design.theme.size.starWarsSizes
import com.kimd13.design.theme.space.StarWarsSpaces
import com.kimd13.design.theme.space.starWarsSpaces
import com.kimd13.design.theme.typography.StarWarsTypography
import com.kimd13.design.theme.typography.starWarsTypography

enum class StarWarsThemeContext {
    LIGHT,
    DARK
}

@Composable
fun StarWarsTheme(
    themeContext: StarWarsThemeContext = getThemeContext(
        isSystemDarkTheme = isSystemInDarkTheme()
    ),
    content: @Composable () -> Unit
) {
    val colors = starWarsColors(themeContext)

    SetStatusBarColor(
        themeContext = themeContext,
        color = colors.primary
    )

    CompositionLocalProvider(
        LocalStarWarsColors provides colors,
        LocalStarWarsElevations provides starWarsElevations(themeContext)

    ) {
        content()
    }
}

private fun getThemeContext(
    isSystemDarkTheme: Boolean
): StarWarsThemeContext =
    if (isSystemDarkTheme) {
        DARK
    } else {
        LIGHT
    }

@Composable
private fun SetStatusBarColor(
    themeContext: StarWarsThemeContext,
    color: Color
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = themeContext == DARK
            }
        }
    }
}

object StarWarsTheme {

    val colors: StarWarsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalStarWarsColors.current

    val elevations: StarWarsElevations
        @Composable
        @ReadOnlyComposable
        get() = LocalStarWarsElevations.current

    val icons: StarWarsIcons = starWarsIcons()

    val sizes: StarWarsSizes = starWarsSizes()

    val spaces: StarWarsSpaces = starWarsSpaces()

    val borders: StarWarsBorders = starWarsBorders()

    val shapes: StarWarsShapes = starWarsShapes()

    val typography: StarWarsTypography = starWarsTypography()
}


private val LocalStarWarsColors = staticCompositionLocalOf<StarWarsColors> {
    error("No StarWarsColors provided.")
}

private val LocalStarWarsElevations = staticCompositionLocalOf<StarWarsElevations> {
    error("No StarWarsElevations provided.")
}