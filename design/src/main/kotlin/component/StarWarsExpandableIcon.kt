package com.kimd13.design.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
internal fun StarWarsExpandableIcon(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val icon = StarWarsTheme.icons.arrowUp
    val iconRotation by animateFloatAsState(
        animationSpec = tween(500),
        targetValue = if (expanded) {
            -180F // Rotate counter-clockwise
        } else {
            0F
        },
        label = ""
    )

    StarWarsIcon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.rotate(iconRotation)
    )
}

@Preview
@Composable
private fun StarWarsExpandableIconPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            StarWarsExpandableIcon(expanded = false)
            StarWarsExpandableIcon(expanded = true)
        }
    }
}

@Preview
@Composable
private fun StarWarsExpandableIconDarkPreview() {
    StarWarsExpandableIconPreview(DARK)
}