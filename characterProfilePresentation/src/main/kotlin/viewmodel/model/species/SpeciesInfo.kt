package com.kimd13.characterprofilepresentation.viewmodel.model.species

import com.kimd13.speciescore.model.Language

/**
 * Singular information about each species.
 */
data class SpeciesInfo(
    val id: Long,
    val name: String,
    val language: Language
)