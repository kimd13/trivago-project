package com.kimd13.design.theme.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class StarWarsIcons internal constructor(
    val search: ImageVector,
    val warning: ImageVector,
    val arrowLeft: ImageVector,
    val arrowRight: ImageVector,
    val arrowUp: ImageVector
)

internal fun starWarsIcons(): StarWarsIcons = StarWarsIcons(
    search = Icons.Default.Search,
    warning = Icons.Default.Warning,
    arrowLeft = Icons.Default.KeyboardArrowLeft,
    arrowRight = Icons.Default.KeyboardArrowRight,
    arrowUp = Icons.Default.KeyboardArrowUp
)