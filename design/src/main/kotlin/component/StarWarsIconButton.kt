package com.kimd13.design.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.modifier.starWarsClickable
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true
) {
    val shape = StarWarsTheme.shapes.circle
    val innerPadding = StarWarsTheme.spaces.medium
    val alpha by animateFloatAsState(
        targetValue = if (enabled) {
            1F
        } else {
            0F
        },
        label = ""
    )
    val clickModifier = if (enabled) {
        Modifier.starWarsClickable { onClick() }
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .clip(shape)
            .then(clickModifier)
            .padding(innerPadding)
            .alpha(alpha)
    ) {
        StarWarsIcon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun StarWarsIconButtonPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            StarWarsIconButton(
                imageVector = StarWarsTheme.icons.warning,
                onClick = {}
            )
            StarWarsIconButton(
                enabled = false,
                imageVector = StarWarsTheme.icons.warning,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun StarWarsIconButtonDarkPreview() {
    StarWarsIconButtonPreview(DARK)
}