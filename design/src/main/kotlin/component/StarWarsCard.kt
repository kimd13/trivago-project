package com.kimd13.design.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.component.modifier.starWarsClickable
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

@Composable
fun StarWarsCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit
) {
    val shape = StarWarsTheme.shapes.roundedMedium
    val innerPadding = StarWarsTheme.spaces.large
    val borderWidth = StarWarsTheme.borders.thin
    val borderColor = StarWarsTheme.colors.onSurface
    val clickModifier = if (onClick != null) {
        Modifier.starWarsClickable { onClick() }
    } else {
        Modifier
    }

    Row(
        content = content,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        modifier = modifier
            .clip(shape)
            .border(borderWidth, borderColor, shape)
            .then(clickModifier)
            .padding(innerPadding)
            .animateContentSize()
    )
}

@Preview
@Composable
private fun StarWarsExpandableCardPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        StarWarsCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StarWarsTheme.sizes.medium)
                    .background(StarWarsTheme.colors.highlight)
            )
        }
    }
}

@Preview
@Composable
private fun StarWarsExpandableCardDarkPreview() {
    StarWarsExpandableCardPreview(DARK)
}