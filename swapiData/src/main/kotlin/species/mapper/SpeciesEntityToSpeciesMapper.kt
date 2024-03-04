package com.kimd13.swapidata.species.mapper

import com.kimd13.speciescore.model.Language
import com.kimd13.speciescore.model.Species
import com.kimd13.swapidata.species.local.model.SpeciesEntity

internal fun transformToSpecies(
    fromSpeciesEntity: SpeciesEntity
): Species = with(fromSpeciesEntity) {
    Species(
        id = id,
        name = name,
        language = language.toLanguage()
    )
}

private fun String?.toLanguage(): Language =
    if (this == null || this == "unknown") {
        Language.Unknown
    } else {
        Language.Recorded(this)
    }