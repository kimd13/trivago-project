package com.kimd13.swapidata.film.remote

import com.kimd13.swapidata.film.remote.model.FilmDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmApi {

    @GET("/api/films/{id}")
    suspend fun getFilm(
        @Path("id") id: Long
    ): FilmDto
}