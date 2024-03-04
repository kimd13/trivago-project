package com.kimd13.charactercore.model

import kotlinx.collections.immutable.ImmutableList

data class Character(
    val id: Long,
    val name: String,
    val height: Height,
    val birthYear: BirthYear,
    val imageUrl: String?,
    val homeWorldId: Long,
    val speciesIds: ImmutableList<Long>,
    val filmIds: ImmutableList<Long>
)