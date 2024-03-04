package com.kimd13.speciescore.model

data class Species(
    val id: Long,
    val name: String,
    val language: Language
)

sealed interface Language {

    data object Unknown : Language

    data class Recorded(
        val record: String
    ) : Language
}