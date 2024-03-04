package com.kimd13.homepresentation.viewmodel.model

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Height

sealed interface HomeCharacterDescription {

    data class CompleteDescription(
        val birthYear: BirthYear.Recorded,
        val height: Height.Recorded
    ) : HomeCharacterDescription

    data class HeightOnlyDescription(
        val height: Height.Recorded
    ) : HomeCharacterDescription

    data class BirthYearOnlyDescription(
        val birthYear: BirthYear.Recorded
    ) : HomeCharacterDescription

    data object VagueDescription : HomeCharacterDescription
}