package com.kimd13.design.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsSurface(
    modifier: Modifier = Modifier,
    color: Color = StarWarsTheme.colors.surface,
    content: @Composable () -> Unit
) {
    Surface(
        color = color,
        content = content,
        modifier = modifier
    )
}

@Preview
@Composable
private fun StarWarsSurfacePreview(
    themeContent: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContent) {
        StarWarsSurface(
            modifier = Modifier
                .size(StarWarsTheme.sizes.xLarge)
        ) {}
    }
}

@Preview
@Composable
private fun StarWarsSurfaceDarkPreview() {
    StarWarsSurfacePreview(DARK)
}