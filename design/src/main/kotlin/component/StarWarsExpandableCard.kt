package com.kimd13.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import kotlinx.collections.immutable.persistentListOf

@Composable
fun StarWarsExpandableCard(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val contentToIconSpacing = StarWarsTheme.spaces.medium

    StarWarsCard(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            // Must weight this component to prevent clipping.
            modifier = Modifier.weight(1F)
        ) {
            content()
        }
        Spacer(modifier = Modifier.width(contentToIconSpacing))
        StarWarsExpandableIcon(expanded)
    }
}

@Preview
@Composable
private fun StarWarsExpandableCardPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            persistentListOf(false, true).forEach { expanded ->
                StarWarsExpandableCard(
                    expanded = expanded,
                    onClick = {},
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(StarWarsTheme.sizes.medium)
                            .background(StarWarsTheme.colors.highlight)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StarWarsExpandableCardDarkPreview() {
    StarWarsExpandableCardPreview(DARK)
}