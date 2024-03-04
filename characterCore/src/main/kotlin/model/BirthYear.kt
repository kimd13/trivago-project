package com.kimd13.charactercore.model

/**
 *  In-universe standard of BBY or ABY.
 *  Before the Battle of Yavin or After the
 *  Battle of Yavin.
 */
enum class RecordedBirthYearUnit {
    BBY,
    ABY
}

sealed interface BirthYear {

    data object Unknown : BirthYear

    data class Recorded(
        val year: Float,
        val unit: RecordedBirthYearUnit
    ) : BirthYear {

        /**
         * Language agnostic string.
         */
        override fun toString(): String =
            "$year ${unit.name}"
    }
}