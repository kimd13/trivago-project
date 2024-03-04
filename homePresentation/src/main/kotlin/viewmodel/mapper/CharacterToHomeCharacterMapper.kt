package com.kimd13.homepresentation.viewmodel.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.Height
import com.kimd13.homepresentation.viewmodel.model.HomeCharacter
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.BirthYearOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.CompleteDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.HeightOnlyDescription
import com.kimd13.homepresentation.viewmodel.model.HomeCharacterDescription.VagueDescription

internal fun transformToHomeCharacter(
    character: Character
): HomeCharacter = HomeCharacter(
    id = character.id,
    name = character.name,
    description = character.getDescription()
)

private fun Character.getDescription(): HomeCharacterDescription {
    val heightRecorded = height is Height.Recorded
    val birthYearRecorded = birthYear is BirthYear.Recorded

    return when {
        heightRecorded && birthYearRecorded -> CompleteDescription(
            birthYear = birthYear as BirthYear.Recorded,
            height = height as Height.Recorded
        )

        heightRecorded -> HeightOnlyDescription(
            height = height as Height.Recorded
        )

        birthYearRecorded -> BirthYearOnlyDescription(
            birthYear = birthYear as BirthYear.Recorded
        )

        else -> VagueDescription
    }
}