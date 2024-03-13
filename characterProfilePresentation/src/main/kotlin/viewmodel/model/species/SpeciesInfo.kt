package com.kimd13.characterprofilepresentation.viewmodel.model.species

import androidx.compose.runtime.Immutable
import com.kimd13.speciescore.model.Language

/**
 * Singular information about each species.
 */
@Immutable
data class SpeciesInfo(
    val id: Long,
    val name: String,
    val language: Language
)