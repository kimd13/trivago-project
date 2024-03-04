package com.kimd13.design.theme.border

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StarWarsBorders internal constructor(
    val thin: Dp
)

internal fun starWarsBorders(): StarWarsBorders = StarWarsBorders(
    thin = 1.dp
)