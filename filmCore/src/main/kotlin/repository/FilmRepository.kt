package com.kimd13.filmcore.repository

import com.kimd13.core.Response
import com.kimd13.filmcore.model.Film
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun fetchFilm(
        id: Long
    ): Flow<Response<Film>>

    fun fetchFilms(
        ids: ImmutableList<Long>
    ): Flow<Response<ImmutableList<Film>>>
}