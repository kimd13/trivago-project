package com.kimd13.homepresentation.viewmodel.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomeCharacter(
    val id: Long,
    val name: String,
    val description: HomeCharacterDescription
)