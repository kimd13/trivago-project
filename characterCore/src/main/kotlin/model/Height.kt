package com.kimd13.charactercore.model

data class FeetInches(
    val feet: Int,
    val inches: Int
)

sealed interface Height {

    data object Unknown : Height

    data class Recorded(
        val inCentimeters: Int,
        val inFeetInches: FeetInches
    ) : Height
}