package com.kimd13.characterprofilepresentation.viewmodel.model.species

import kotlinx.collections.immutable.ImmutableList

data class SpeciesSection(
    // Character can be of multiple species.
    val infoListing: ImmutableList<SpeciesInfo>
)