package com.kimd13.design.theme.shape

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class StarWarsShapes internal constructor(
    val circle: Shape,
    val roundedSmall: Shape,
    val roundedMedium: Shape,
    val roundedLarge: Shape
)

internal fun starWarsShapes(): StarWarsShapes = StarWarsShapes(
    circle = CircleShape,
    roundedSmall = RoundedCornerShape(4.dp),
    roundedMedium = RoundedCornerShape(8.dp),
    roundedLarge = RoundedCornerShape(16.dp)
)