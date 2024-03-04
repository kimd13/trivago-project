package com.kimd13.planetcore.model

data class Planet(
    val id: Long,
    val name: String,
    val population: Population
)

sealed interface Population {

    data object Unknown : Population

    data class Recorded(
        val record: Long
    ) : Population
}