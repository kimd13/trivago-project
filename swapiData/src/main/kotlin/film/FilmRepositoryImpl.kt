package com.kimd13.swapidata.film

import com.kimd13.core.Response
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.core.mapImmutable
import com.kimd13.filmcore.model.Film
import com.kimd13.filmcore.repository.FilmRepository
import com.kimd13.swapidata.di.SwapiDataDispatcher
import com.kimd13.swapidata.film.local.FilmDao
import com.kimd13.swapidata.film.local.model.FilmEntity
import com.kimd13.swapidata.film.mapper.transformToFilm
import com.kimd13.swapidata.film.mapper.transformToFilmEntity
import com.kimd13.swapidata.film.remote.FilmApi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi,
    private val dao: FilmDao,
    @SwapiDataDispatcher private val dispatcher: CoroutineDispatcher
) : FilmRepository {

    override fun fetchFilm(
        id: Long
    ): Flow<Response<Film>> = flow {
        emit(Loading)
        try {
            val updatedEntity = syncLocalFilmAndGet(id)
            emit(Success(transformToFilm(updatedEntity)))
        } catch (e: IOException) {
            emit(getFilmResponseOnFailure(id, e))
        } catch (e: HttpException) {
            emit(getFilmResponseOnFailure(id, e))
        }
    }

    private suspend fun syncLocalFilmAndGet(id: Long): FilmEntity {
        val dto = api.getFilm(id)
        val entity = transformToFilmEntity(dto)
        dao.upsert(entity)
        return entity
    }

    private suspend fun getFilmResponseOnFailure(
        id: Long,
        e: Exception
    ): Response<Film> {
        val entity = dao.get(id)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, transformToFilm(entity))
        }
    }

    override fun fetchFilms(
        ids: ImmutableList<Long>
    ): Flow<Response<ImmutableList<Film>>> = flow {
        emit(Loading)
        try {
            val updatedEntities = syncLocalFilmsAndGet(ids)
            emit(Success(updatedEntities.mapImmutable(::transformToFilm)))
        } catch (e: IOException) {
            emit(getFilmsResponseOnFailure(ids, e))
        } catch (e: HttpException) {
            emit(getFilmsResponseOnFailure(ids, e))
        }
    }

    private suspend fun syncLocalFilmsAndGet(ids: List<Long>): List<FilmEntity> =
        withContext(dispatcher) {
            val entities = ids.map {
                async { transformToFilmEntity(api.getFilm(it)) }
            }.awaitAll()

            dao.upsert(entities)
            return@withContext entities
        }

    private suspend fun getFilmsResponseOnFailure(
        ids: List<Long>,
        e: Exception
    ): Response<ImmutableList<Film>> {
        val entity = dao.get(ids)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, entity.mapImmutable(::transformToFilm))
        }
    }
}