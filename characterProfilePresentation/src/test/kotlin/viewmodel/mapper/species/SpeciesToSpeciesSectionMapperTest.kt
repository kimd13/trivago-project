package com.kimd13.characterprofilepresentation.viewmodel.mapper.species

import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.species.SpeciesSection
import com.kimd13.speciescore.model.Language
import com.kimd13.speciescore.model.Species
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesToSpeciesSectionMapperTest {

    @Test
    fun `transformToSpeciesSection returns correct ui model given proper domain model`() {
        // Given
        val id = 1L
        val name = "Human"
        val language = Language.Unknown
        val species = Species(
            id = id,
            name = name,
            language = language
        )

        // When
        val section = transformToSpeciesSection(persistentListOf(species))

        // Then
        val expectation = SpeciesSection(
            persistentListOf(
                SpeciesInfo(
                    id = id,
                    name = name,
                    language = language
                )
            )
        )
        assertEquals(expectation, section)
    }
}