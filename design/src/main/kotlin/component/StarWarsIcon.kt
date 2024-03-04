package com.kimd13.design.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = StarWarsTheme.colors.onSurface,
    contentDescription: String? = null,
) {
    Icon(
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Preview
@Composable
private fun StarWarsIconPreview(
    themeContent: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContent) {
        StarWarsIcon(imageVector = StarWarsTheme.icons.warning)
    }
}

@Preview
@Composable
private fun StarWarsIconDarkPreview() {
    StarWarsIconPreview(DARK)
}