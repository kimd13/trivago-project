package com.kimd13.swapidata.film.mapper

import com.kimd13.filmcore.model.Film
import com.kimd13.swapidata.film.local.model.FilmEntity

internal fun transformToFilm(
    fromFilmEntity: FilmEntity
): Film = with(fromFilmEntity) {
    Film(
        id = id,
        title = title,
        openingCrawl = openingCrawl
    )
}