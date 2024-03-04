package com.kimd13.swapidata.species.mapper

import com.kimd13.swapidata.mapper.toId
import com.kimd13.swapidata.species.local.model.SpeciesEntity
import com.kimd13.swapidata.species.remote.model.SpeciesDto

internal fun transformToSpeciesEntity(
    fromSpeciesDto: SpeciesDto
): SpeciesEntity = with(fromSpeciesDto) {
    SpeciesEntity(
        id = self_endpoint.toId(),
        name = name,
        language = language
    )
}