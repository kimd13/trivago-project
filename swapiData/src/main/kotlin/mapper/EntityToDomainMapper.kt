package com.kimd13.swapidata.mapper

import com.kimd13.core.mapImmutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Stored interpretation mapping to domain appropriate format.
 */
internal fun String.toIds(): ImmutableList<Long> {
    return if (isBlank()) {
        persistentListOf()
    } else {
        split(",").mapImmutable { it.toLong() }
    }
}