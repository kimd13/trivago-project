package com.kimd13.homepresentation.viewmodel.model

import kotlinx.collections.immutable.ImmutableList

data class HomePage(
    val number: Int,
    val nextPageExists: Boolean,
    val nextPage: () -> Unit,
    val previousPageExists: Boolean,
    val previousPage: () -> Unit,
    val characters: ImmutableList<HomeCharacter>
)