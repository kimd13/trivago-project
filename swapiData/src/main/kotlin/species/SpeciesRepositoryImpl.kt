package com.kimd13.swapidata.species

import com.kimd13.core.Response
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.core.mapImmutable
import com.kimd13.speciescore.model.Species
import com.kimd13.speciescore.repository.SpeciesRepository
import com.kimd13.swapidata.di.SwapiDataDispatcher
import com.kimd13.swapidata.species.local.SpeciesDao
import com.kimd13.swapidata.species.local.model.SpeciesEntity
import com.kimd13.swapidata.species.mapper.transformToSpecies
import com.kimd13.swapidata.species.mapper.transformToSpeciesEntity
import com.kimd13.swapidata.species.remote.SpeciesApi
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

class SpeciesRepositoryImpl @Inject constructor(
    private val api: SpeciesApi,
    private val dao: SpeciesDao,
    @SwapiDataDispatcher private val dispatcher: CoroutineDispatcher
) : SpeciesRepository {

    override fun fetchSpecies(
        id: Long
    ): Flow<Response<Species>> = flow {
        emit(Loading)
        try {
            val updatedEntity = syncLocalSpeciesAndGet(id)
            emit(Success(transformToSpecies(updatedEntity)))
        } catch (e: IOException) {
            emit(getLocalSpeciesResponse(id, e))
        } catch (e: HttpException) {
            emit(getLocalSpeciesResponse(id, e))
        }
    }

    private suspend fun syncLocalSpeciesAndGet(id: Long): SpeciesEntity {
        val dto = api.getSpecies(id)
        val entity = transformToSpeciesEntity(dto)
        dao.upsert(entity)
        return entity
    }

    private suspend fun getLocalSpeciesResponse(
        id: Long,
        e: Exception
    ): Response<Species> {
        val entity = dao.get(id)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, transformToSpecies(entity))
        }
    }

    override fun fetchSpecies(
        ids: ImmutableList<Long>
    ): Flow<Response<ImmutableList<Species>>> = flow {
        emit(Loading)
        try {
            val updatedEntities = syncLocalSpeciesAndGet(ids)
            emit(Success(updatedEntities.mapImmutable(::transformToSpecies)))
        } catch (e: IOException) {
            emit(getSpeciesResponseOnFailure(ids, e))
        } catch (e: HttpException) {
            emit(getSpeciesResponseOnFailure(ids, e))
        }
    }

    private suspend fun syncLocalSpeciesAndGet(ids: List<Long>): List<SpeciesEntity> =
        withContext(dispatcher) {
            val entities = ids.map {
                async { transformToSpeciesEntity(api.getSpecies(it)) }
            }.awaitAll()

            dao.upsert(entities)
            return@withContext entities
        }

    private suspend fun getSpeciesResponseOnFailure(
        ids: List<Long>,
        e: Exception
    ): Response<ImmutableList<Species>> {
        val entity = dao.get(ids)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, entity.mapImmutable(::transformToSpecies))
        }
    }
}