package com.kimd13.homepresentation.viewmodel.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomePage(
    val number: Int,
    val nextPageExists: Boolean,
    val nextPage: () -> Unit,
    val previousPageExists: Boolean,
    val previousPage: () -> Unit,
    val characters: ImmutableList<HomeCharacter>
)