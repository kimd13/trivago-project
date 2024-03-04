package com.kimd13.design.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.modifier.starWarsClickable
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = StarWarsTheme.shapes.circle
    val borderWidth = StarWarsTheme.borders.thin
    val borderColor = StarWarsTheme.colors.onSurface
    val innerPadding = StarWarsTheme.spaces.medium

    Box(
        modifier = modifier
            .clip(shape)
            .border(borderWidth, borderColor, shape)
            .starWarsClickable { onClick() }
            .padding(innerPadding)
    ) {
        StarWarsText(text)
    }
}

@Preview
@Composable
private fun StarWarsTextButtonPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsTextButton(
            text = "Click me!",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun StarWarsTextButtonDarkPreview() {
    StarWarsTextButtonPreview(DARK)
}