package com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal

import androidx.compose.runtime.Immutable
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Height

@Immutable
data class CharacterPersonalInfo(
    val name: String,
    val birthYear: BirthYear,
    val height: Height
)