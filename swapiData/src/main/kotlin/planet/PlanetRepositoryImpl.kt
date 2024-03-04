package com.kimd13.swapidata.planet

import com.kimd13.core.Response
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.planetcore.model.Planet
import com.kimd13.planetcore.repository.PlanetRepository
import com.kimd13.swapidata.planet.local.PlanetDao
import com.kimd13.swapidata.planet.local.model.PlanetEntity
import com.kimd13.swapidata.planet.mapper.transformToPlanet
import com.kimd13.swapidata.planet.mapper.transformToPlanetEntity
import com.kimd13.swapidata.planet.remote.PlanetApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val api: PlanetApi,
    private val dao: PlanetDao
) : PlanetRepository {

    override fun fetchPlanet(
        id: Long
    ): Flow<Response<Planet>> = flow {
        emit(Loading)
        try {
            val updatedEntity = syncLocalPlanetAndGet(id)
            emit(Success(transformToPlanet(updatedEntity)))
        } catch (e: IOException) {
            emit(getPlanetResponseOnFailure(id, e))
        } catch (e: HttpException) {
            emit(getPlanetResponseOnFailure(id, e))
        }
    }

    private suspend fun syncLocalPlanetAndGet(id: Long): PlanetEntity {
        val dto = api.getPlanet(id)
        val entity = transformToPlanetEntity(dto)
        dao.upsert(entity)
        return entity
    }

    private suspend fun getPlanetResponseOnFailure(
        id: Long,
        e: Exception
    ): Response<Planet> {
        val entity = dao.get(id)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, transformToPlanet(entity))
        }
    }
}