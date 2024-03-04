package com.kimd13.swapidata.character.mapper

import com.kimd13.charactercore.model.Character
import com.kimd13.core.Page
import com.kimd13.core.mapImmutable
import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.character.remote.model.CharactersPageDto
import kotlinx.collections.immutable.ImmutableList
import java.net.URI

internal fun transformToCharactersPage(
    fromCharactersPageDto: CharactersPageDto,
    currentPageNumber: Int,
    withCharacterEntities: List<CharacterEntity>
): Page<ImmutableList<Character>> = with(fromCharactersPageDto) {
    Page(
        currentPageNumber = currentPageNumber,
        nextPageNumber = next_endpoint?.toPageNumber(),
        previousPageNumber = previous_endpoint?.toPageNumber(),
        results = withCharacterEntities.mapImmutable(::transformToCharacter)
    )
}

/**
 * [this] must be in the format of a swapi search endpoint.
 * (e.g. "https://swapi.dev/api/people/?search=a&page=11").
 */
private fun String.toPageNumber(): Int {
    val query = URI(this).query
    // This regex pattern any 1 or more digits after &page=
    val match = Regex("(?!&page=)(\\d+)").find(query)
    return match!!.value.toInt()
}