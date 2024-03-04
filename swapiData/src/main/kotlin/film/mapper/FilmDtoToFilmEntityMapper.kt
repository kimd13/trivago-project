package com.kimd13.swapidata.film.mapper

import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.film.remote.model.FilmDto
import com.kimd13.swapidata.mapper.toId

internal fun transformToFilmEntity(
    fromFilmDto: FilmDto
): FilmEntity = with(fromFilmDto) {
    FilmEntity(
        id = self_endpoint.toId(),
        title = title,
        openingCrawl = opening_crawl
    )
}
