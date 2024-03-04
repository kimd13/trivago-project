package com.kimd13.design.theme.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class StarWarsTypography internal constructor(
    val title: TextStyle,
    val body: TextStyle,
    val label: TextStyle
)

internal fun starWarsTypography(): StarWarsTypography = StarWarsTypography(
    title = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    ),
    body = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Monospace
    ),
    label = TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
)