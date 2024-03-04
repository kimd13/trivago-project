package com.kimd13.design.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarWarsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    val trailingIcon = StarWarsTheme.icons.search
    val containerColor = StarWarsTheme.colors.surfaceElevated
    val focusedTextColor = StarWarsTheme.colors.onSurface
    val unfocusedTextColor = StarWarsTheme.colors.onSurface.copy(0.5F)
    val progressIndicatorColor = StarWarsTheme.colors.primary
    val progressIndicatorTrackColor = progressIndicatorColor.copy(0.5F)
    val cursorColor = StarWarsTheme.colors.primary
    val elevation = StarWarsTheme.elevations.slightlyRaised
    val shape = StarWarsTheme.shapes.circle
    val width = StarWarsTheme.sizes.xLarge
    val textStyle = StarWarsTheme.typography.body

    Column(
        modifier
            .shadow(elevation, shape)
            .width(width)
            .clip(shape)
            .animateContentSize()
    ) {
        TextField(
            value = query,
            textStyle = textStyle,
            onValueChange = onQueryChange,
            trailingIcon = { StarWarsIcon(trailingIcon) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = containerColor,
                focusedTextColor = focusedTextColor,
                unfocusedTextColor = unfocusedTextColor,
                cursorColor = cursorColor,
                // Remove bottom line indicator line.
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (loading) {
            LinearProgressIndicator(
                color = progressIndicatorColor,
                trackColor = progressIndicatorTrackColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun StarWarsSearchBarPreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            persistentListOf(false, true).forEach { loading ->
                StarWarsSearchBar(
                    query = "Foo",
                    onQueryChange = {},
                    loading = loading
                )
            }
        }
    }
}

@Preview
@Composable
private fun StarWarsSearchBarDarkPreview() {
    StarWarsSearchBarPreview(DARK)
}