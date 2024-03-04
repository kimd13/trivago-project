package com.kimd13.characterprofilepresentation.viewmodel.mapper.species

import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.core.mapImmutable
import com.kimd13.speciescore.model.Species
import kotlinx.collections.immutable.ImmutableList

/**
 * [Species] list domain model mapping to [SpeciesSection] UI model.
 */
internal fun transformToSpeciesSection(
    species: ImmutableList<Species>
): SpeciesSection = with(species) {
    SpeciesSection(
        infoListing = mapImmutable(::transformToSpeciesInfo)
    )
}

/**
 * [Species] domain model mapping to [SpeciesInfo] UI model.
 */
internal fun transformToSpeciesInfo(
    species: Species
): SpeciesInfo = with(species) {
    SpeciesInfo(
        id = id,
        name = name,
        language = language
    )
}