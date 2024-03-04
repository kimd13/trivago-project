package com.kimd13.design.theme.space

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StarWarsSpaces internal constructor(
    val small: Dp,
    val medium: Dp,
    val large: Dp
)

internal fun starWarsSpaces(): StarWarsSpaces = StarWarsSpaces(
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp
)