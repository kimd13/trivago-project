package com.kimd13.design.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = StarWarsTheme.colors.onSurface,
    style: TextStyle = StarWarsTheme.typography.body,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        color = color,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Preview
@Composable
private fun StarWarsTextPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsText(
            text = "Preview"
        )
    }
}

@Preview
@Composable
private fun StarWarsTextDarkPreview() {
    StarWarsTextPreview(DARK)
}