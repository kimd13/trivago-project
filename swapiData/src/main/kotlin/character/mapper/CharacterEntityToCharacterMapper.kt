package com.kimd13.swapidata.character.mapper

import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit.ABY
import com.kimd13.charactercore.model.RecordedBirthYearUnit.BBY
import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.mapper.toIds
import kotlin.math.roundToInt
import com.kimd13.charactercore.model.BirthYear.Recorded as BirthYearRecorded
import com.kimd13.charactercore.model.BirthYear.Unknown as BirthYearUnknown
import com.kimd13.charactercore.model.Height.Recorded as HeightRecorded
import com.kimd13.charactercore.model.Height.Unknown as HeightUnknown

private const val CENTIMETER_TO_INCH_EQUIVALENT: Double = 0.393701
private const val FOOT_TO_CENTIMETER_EQUIVALENT: Int = 12

internal fun transformToCharacter(
    fromCharacterEntity: CharacterEntity
): Character = with(fromCharacterEntity) {
    Character(
        id = id,
        name = name,
        height = height.toHeight(),
        birthYear = birthYear.toBirthYear(),
        imageUrl = null,
        homeWorldId = homeWorldId,
        speciesIds = speciesIds.toIds(),
        filmIds = filmIds.toIds()
    )
}

private fun String.toHeight(): Height = if (this == "unknown") {
    HeightUnknown
} else {
    val inCentimeters = toInt()
    HeightRecorded(
        inCentimeters = inCentimeters,
        inFeetInches = inCentimeters.inFeetInches()
    )
}

/**
 * Helper [FeetInches] initializer that rounds to the nearest inch.
 */
private fun Int.inFeetInches(): FeetInches {
    val inInches = CENTIMETER_TO_INCH_EQUIVALENT * this
    val inFeet = (inInches / FOOT_TO_CENTIMETER_EQUIVALENT).toInt() // Round down.
    val inInchesLeftover =
        (inInches % FOOT_TO_CENTIMETER_EQUIVALENT).roundToInt() // Round to nearest inch.
    return FeetInches(inFeet, inInchesLeftover)
}

/**
 * [this] must be in the format of a swapi year (e.g. 19.1BBY).
 */
private fun String.toBirthYear(): BirthYear {
    if (this == "unknown") {
        return BirthYearUnknown
    }

    val abyStartIndex = indexOf("ABY")
    val bbyStartIndex = indexOf("BBY")

    return when {
        abyStartIndex != -1 -> BirthYearRecorded(
            year = substring(0, abyStartIndex).toFloat(),
            unit = ABY
        )

        bbyStartIndex != -1 -> BirthYearRecorded(
            year = substring(0, bbyStartIndex).toFloat(),
            unit = BBY
        )

        else -> throw IllegalStateException("Birth year string $this is not recognized.")
    }
}