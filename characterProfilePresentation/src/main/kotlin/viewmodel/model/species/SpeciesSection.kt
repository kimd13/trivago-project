package com.kimd13.characterprofilepresentation.viewmodel.model.species

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SpeciesSection(
    // Character can be of multiple species.
    val infoListing: ImmutableList<SpeciesInfo>
)