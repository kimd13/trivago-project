package com.kimd13.characterprofilepresentation.viewmodel.model.homeworld

import androidx.compose.runtime.Immutable
import com.kimd13.planetcore.model.Population

@Immutable
data class HomeWorldInfo(
    val name: String,
    val population: Population
)