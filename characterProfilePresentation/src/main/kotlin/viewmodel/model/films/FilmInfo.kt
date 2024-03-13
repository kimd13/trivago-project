package com.kimd13.characterprofilepresentation.viewmodel.model.films

import androidx.compose.runtime.Immutable

@Immutable
data class FilmInfo(
    val id: Long,
    val title: String,
    val openingCrawl: String
)