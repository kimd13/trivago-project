package com.kimd13.swapidata.character.mapper

import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.character.remote.model.CharacterDto
import com.kimd13.swapidata.mapper.toId
import com.kimd13.swapidata.mapper.toIds

internal fun transformToCharacterEntity(
    fromCharacterDto: CharacterDto
): CharacterEntity = with(fromCharacterDto) {
    CharacterEntity(
        id = self_endpoint.toId(),
        name = name,
        height = height,
        birthYear = birth_year,
        homeWorldId = homeworld_endpoint.toId(),
        speciesIds = species_endpoints.toIds(),
        filmIds = films_endpoints.toIds()
    )
}