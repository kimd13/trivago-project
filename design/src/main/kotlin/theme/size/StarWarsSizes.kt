package com.kimd13.design.theme.size

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StarWarsSizes internal constructor(
    val xSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val xLarge: Dp
)

internal fun starWarsSizes(): StarWarsSizes = StarWarsSizes(
    xSmall = 12.dp,
    small = 24.dp,
    medium = 48.dp,
    large = 96.dp,
    xLarge = 256.dp
)