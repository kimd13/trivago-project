package com.kimd13.swapidata.character.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit.BBY
import com.kimd13.swapidata.character.local.model.CharacterEntity
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterEntityToCharacterMapperTest {

    @Test
    fun `transformToCharacterEntity returns correct domain model when given correct entity`() {
        val id = 1L
        val name = "Luke Skywalker"
        val homeWorldId = 1L
        val entity = CharacterEntity(
            id = id,
            name = name,
            height = "172",
            birthYear = "19BBY",
            homeWorldId = homeWorldId,
            speciesIds = "1,2",
            filmIds = "1,2"
        )

        // When
        val domain = transformToCharacter(entity)

        // Then
        val expectation = Character(
            id = id,
            name = name,
            height = Height.Recorded(172, FeetInches(5, 8)),
            birthYear = BirthYear.Recorded(19F, BBY),
            imageUrl = null,
            homeWorldId = homeWorldId,
            speciesIds = persistentListOf(1, 2),
            filmIds = persistentListOf(1, 2)
        )
        assertEquals(expectation, domain)
    }

    @Test
    fun `transformToCharacterEntity returns Unknown(s) when given unknown values`() {
        val id = 1L
        val name = "Luke Skywalker"
        val homeWorldId = 1L
        val entity = CharacterEntity(
            id = id,
            name = name,
            height = "unknown",
            birthYear = "unknown",
            homeWorldId = homeWorldId,
            speciesIds = "1,2",
            filmIds = "1,2"
        )

        // When
        val domain = transformToCharacter(entity)

        // Then
        val expectation = Character(
            id = id,
            name = name,
            height = Height.Unknown,
            birthYear = BirthYear.Unknown,
            imageUrl = null,
            homeWorldId = homeWorldId,
            speciesIds = persistentListOf(1, 2),
            filmIds = persistentListOf(1, 2)
        )
        assertEquals(expectation, domain)
    }
}