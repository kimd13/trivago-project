package com.kimd13.design.component.modifier

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.kimd13.design.theme.StarWarsTheme

/**
 * Creates and remembers a Ripple using the specified color.
 */
@Composable
fun rememberStarWarsRipple(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = StarWarsTheme.colors.primary
): Indication = rememberRipple(bounded, radius, color)

/**
 * Configure component to receive clicks via input or accessibility "click" event.
 */
fun Modifier.starWarsClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = this.clickable(
    interactionSource = interactionSource,
    indication = indication,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = onClick
)

fun Modifier.starWarsClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    this.starWarsClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberStarWarsRipple(),
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}
