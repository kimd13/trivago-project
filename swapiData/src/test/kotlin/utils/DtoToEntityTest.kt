package com.kimd13.swapidata.utils

import com.kimd13.swapidata.mapper.toId
import com.kimd13.swapidata.mapper.toIds
import org.junit.Assert.assertEquals
import org.junit.Test

class DtoToEntityTest {

    @Test
    fun `toId given a character endpoint from swapi expect id in the form of long`() {
        // Given
        val expectedId = 1L
        val characterEndpoint = "https://swapi.dev/api/people/$expectedId/"

        // When
        val id = characterEndpoint.toId()

        // Then
        assertEquals(expectedId, id)
    }

    @Test
    fun `toId given a film endpoint from swapi expect id in the form of long`() {
        // Given
        val expectedId = 1L
        val filmEndpoint = "https://swapi.dev/api/films/$expectedId/"

        // When
        val id = filmEndpoint.toId()

        // Then
        assertEquals(expectedId, id)
    }

    @Test
    fun `toID given a species endpoint from swapi expect id in the form of long`() {
        // Given
        val expectedId = 1L
        val speciesEndpoint = "https://swapi.dev/api/species/$expectedId/"

        // When
        val id = speciesEndpoint.toId()

        // Then
        assertEquals(expectedId, id)
    }

    @Test
    fun `toId given a planet endpoint from swapi expect id in the form of long`() {
        // Given
        val expectedId = 1L
        val planetEndpoint = "https://swapi.dev/api/planets/$expectedId/"

        // When
        val id = planetEndpoint.toId()

        // Then
        assertEquals(expectedId, id)
    }

    @Test
    fun `toId given a endpoint with a double digit id from swapi expect id in the form of long`() {
        // Given
        val expectedId = 11L
        val planetEndpoint = "https://swapi.dev/api/planets/$expectedId/"

        // When
        val id = planetEndpoint.toId()

        // Then
        assertEquals(expectedId, id)
    }

    @Test
    fun `toId given a list of endpoints expect proper string interpretation`() {
        // Given
        val characters = listOf(
            "https://swapi.dev/api/people/1/",
            "https://swapi.dev/api/people/2/",
            "https://swapi.dev/api/people/3/"
        )

        // When
        val interpretation = characters.toIds()

        // Then
        assertEquals("1,2,3", interpretation)
    }

    @Test
    fun `toId given an empty list expect empty string`() {
        // Given
        val characters = listOf<String>()

        // When
        val interpretation = characters.toIds()

        // Then
        assertEquals("", interpretation)
    }
}