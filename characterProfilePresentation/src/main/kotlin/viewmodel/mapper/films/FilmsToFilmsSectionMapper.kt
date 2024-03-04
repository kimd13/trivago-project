package com.kimd13.characterprofilepresentation.viewmodel.mapper.films

import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.films.FilmsSection
import com.kimd13.core.mapImmutable
import com.kimd13.filmcore.model.Film
import kotlinx.collections.immutable.ImmutableList

/**
 * [Film] list domain model mapping to [FilmsSection] UI model.
 */
internal fun transformToFilmsSection(
    fromFilms: ImmutableList<Film>
): FilmsSection = with(fromFilms) {
    FilmsSection(
        infoListing = mapImmutable(::transformToFilmInfo)
    )
}

/**
 * [Film] domain model mapping to [FilmInfo] UI model.
 */
internal fun transformToFilmInfo(
    fromFilm: Film
): FilmInfo = with(fromFilm) {
    FilmInfo(
        id = id,
        title = title,
        openingCrawl = openingCrawl
    )
}