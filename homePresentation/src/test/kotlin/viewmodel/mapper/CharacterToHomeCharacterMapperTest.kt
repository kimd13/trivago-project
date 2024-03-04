package com.kimd13.homepresentation.viewmodel.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.BirthYearOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.CompleteDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.HeightOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.VagueDescription
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterToHomeCharacterMapperTest {

    @Test
    fun `transformToHomeCharacter returns correct ui model given proper domain model`() {
        // Given
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Recorded(172, FeetInches(5, 8))
        val birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY)
        val character = Character(
            id = id,
            name = name,
            height = height,
            birthYear = birthYear,
            imageUrl = null,
            homeWorldId = 1,
            speciesIds = persistentListOf(),
            filmIds = persistentListOf()
        )

        // When
        val homeCharacter = transformToHomeCharacter(character)

        // Then
        val expectation = HomeCharacter(
            id = id,
            name = name,
            description = CompleteDescription(
                height = height,
                birthYear = birthYear
            )
        )
        assertEquals(expectation, homeCharacter)
    }

    @Test
    fun `transformToHomeCharacter returns correct ui model given Unknown height`() {
        // Given
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Unknown
        val birthYear = BirthYear.Recorded(19F, RecordedBirthYearUnit.BBY)
        val character = Character(
            id = id,
            name = name,
            height = height,
            birthYear = birthYear,
            imageUrl = null,
            homeWorldId = 1,
            speciesIds = persistentListOf(),
            filmIds = persistentListOf()
        )

        // When
        val homeCharacter = transformToHomeCharacter(character)

        // Then
        val expectation = HomeCharacter(
            id = id,
            name = name,
            description = BirthYearOnlyDescription(
                birthYear = birthYear
            )
        )
        assertEquals(expectation, homeCharacter)
    }

    @Test
    fun `transformToHomeCharacter returns correct ui model given Unknown birth year`() {
        // Given
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Recorded(172, FeetInches(5, 8))
        val birthYear = BirthYear.Unknown
        val character = Character(
            id = id,
            name = name,
            height = height,
            birthYear = birthYear,
            imageUrl = null,
            homeWorldId = 1,
            speciesIds = persistentListOf(),
            filmIds = persistentListOf()
        )

        // When
        val homeCharacter = transformToHomeCharacter(character)

        // Then
        val expectation = HomeCharacter(
            id = id,
            name = name,
            description = HeightOnlyDescription(
                height = height
            )
        )
        assertEquals(expectation, homeCharacter)
    }

    @Test
    fun `transformToHomeCharacter returns correct ui model given Unknown height and birth year`() {
        // Given
        val id = 1L
        val name = "Luke Skywalker"
        val height = Height.Unknown
        val birthYear = BirthYear.Unknown
        val character = Character(
            id = id,
            name = name,
            height = height,
            birthYear = birthYear,
            imageUrl = null,
            homeWorldId = 1,
            speciesIds = persistentListOf(),
            filmIds = persistentListOf()
        )

        // When
        val homeCharacter = transformToHomeCharacter(character)

        // Then
        val expectation = HomeCharacter(
            id = id,
            name = name,
            description = VagueDescription
        )
        assertEquals(expectation, homeCharacter)
    }
}