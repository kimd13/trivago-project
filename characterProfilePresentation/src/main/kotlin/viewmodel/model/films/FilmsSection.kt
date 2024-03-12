package com.kimd13.characterprofilepresentation.viewmodel.model.films

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class FilmsSection(
    val infoListing: ImmutableList<FilmInfo>
)