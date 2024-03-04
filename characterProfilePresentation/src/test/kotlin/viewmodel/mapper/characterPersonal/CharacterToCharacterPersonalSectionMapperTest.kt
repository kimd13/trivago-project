package com.kimd13.characterprofilepresentation.viewmodel.mapper.characterPersonal

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.Height
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterToCharacterPersonalSectionMapperTest {

    @Test
    fun `transformToCharacterPersonalSection returns correct ui model given proper domain model`() {
        // Given
        val name = "Luke Skywalker"
        val height = Height.Unknown
        val birthYear = BirthYear.Unknown
        val character = Character(
            id = 1L,
            name = name,
            height = height,
            birthYear = birthYear,
            imageUrl = null,
            homeWorldId = 1L,
            speciesIds = persistentListOf(),
            filmIds = persistentListOf()
        )

        // When
        val section = transformToCharacterPersonalSection(character)

        // Then
        val expectation = CharacterPersonalSection(
            CharacterPersonalInfo(
                name = name,
                birthYear = birthYear,
                height = height
            )
        )
        assertEquals(expectation, section)
    }
}