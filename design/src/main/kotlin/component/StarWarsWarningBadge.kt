package com.kimd13.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kimd13.design.R
import com.kimd13.design.component.StarWarsWarning.DATA_POTENTIALLY_OUT_OF_SYNC
import com.kimd13.design.component.StarWarsWarning.FAILURE
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.design.theme.StarWarsThemeContext
import com.kimd13.design.theme.StarWarsThemeContext.DARK
import com.kimd13.design.theme.StarWarsThemeContext.LIGHT

enum class StarWarsWarning {
    FAILURE,
    DATA_POTENTIALLY_OUT_OF_SYNC
}

@Composable
fun StarWarsWarningBadge(
    warning: StarWarsWarning,
    modifier: Modifier = Modifier
) {
    val message = when (warning) {
        FAILURE -> stringResource(id = R.string.something_went_wrong)
        DATA_POTENTIALLY_OUT_OF_SYNC -> stringResource(id = R.string.data_out_of_sync)
    }
    val containerColor = when (warning) {
        FAILURE -> StarWarsTheme.colors.error
        DATA_POTENTIALLY_OUT_OF_SYNC -> StarWarsTheme.colors.highlight
    }
    val contentColor = when (warning) {
        FAILURE -> StarWarsTheme.colors.onError
        DATA_POTENTIALLY_OUT_OF_SYNC -> StarWarsTheme.colors.onHighlight
    }
    val indicator = StarWarsTheme.icons.warning
    val height = StarWarsTheme.sizes.small
    val shape = StarWarsTheme.shapes.circle
    val contentSpacing = StarWarsTheme.spaces.small
    val innerPadding = StarWarsTheme.spaces.small
    val iconSize = StarWarsTheme.sizes.xSmall
    val textStyle = StarWarsTheme.typography.label

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(contentSpacing),
        modifier = modifier
            .height(height)
            .background(containerColor, shape)
            .padding(innerPadding)
    ) {
        StarWarsIcon(
            imageVector = indicator,
            tint = contentColor,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
        StarWarsText(
            text = message,
            style = textStyle,
            color = contentColor
        )
    }
}

@Preview
@Composable
private fun StarWarsWarningBadgePreview(
    themeContext: StarWarsThemeContext = LIGHT
) {
    StarWarsTheme(themeContext) {
        Column {
            StarWarsWarning.entries.forEach {
                StarWarsWarningBadge(it)
            }
        }
    }
}

@Preview
@Composable
private fun StarWarsWarningBadgeDarkPreview() {
    StarWarsWarningBadgePreview(DARK)
}