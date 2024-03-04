package com.kimd13.swapidata.mapper

import java.net.URI

/**
 * [this] must be in the format of a swapi endpoint that contains an id
 * (e.g. "https://swapi.dev/api/people/11/").
 */
internal fun String.toId(): Long {
    val uri = URI(this)
    val segments = uri.path.split("/").dropLastWhile { it.isEmpty() }
    return segments[segments.size - 1].toLong()
}

/**
 * Each item in [this] must be in the format of a swapi endpoint that contains an id
 * (e.g. "https://swapi.dev/api/people/1/").
 * Returns string interpretation to be stored.
 */
internal fun List<String>.toIds(): String = map {
    it.toId()
}.joinToString(",")