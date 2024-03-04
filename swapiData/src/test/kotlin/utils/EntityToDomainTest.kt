package com.kimd13.swapidata.utils

import com.kimd13.swapidata.mapper.toIds
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityToDomainTest {

    @Test
    fun `toIds given a string interpretation of ids expect proper list of ids`() {
        // Given
        val interpretation = "1,2,3"

        // When
        val ids = interpretation.toIds()

        // Then
        assertEquals(persistentListOf(1L, 2L, 3L), ids)
    }

    @Test
    fun `toIds given a string interpretation of empty ids expect proper empty list`() {
        // Given
        val interpretation = ""

        // When
        val ids = interpretation.toIds()

        // Then
        assertEquals(persistentListOf<Long>(), ids)
    }
}