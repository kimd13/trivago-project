package com.kimd13.swapidata.character

import com.kimd13.charactercore.model.Character
import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.core.Page
import com.kimd13.core.Response
import com.kimd13.core.Response.Backup
import com.kimd13.core.Response.Failure
import com.kimd13.core.Response.Loading
import com.kimd13.core.Response.Success
import com.kimd13.swapidata.character.local.CharacterDao
import com.kimd13.swapidata.character.local.model.CharacterEntity
import com.kimd13.swapidata.character.mapper.transformToCharacter
import com.kimd13.swapidata.character.mapper.transformToCharacterEntity
import com.kimd13.swapidata.character.mapper.transformToCharactersPage
import com.kimd13.swapidata.character.remote.CharacterApi
import com.kimd13.swapidata.character.remote.model.CharactersPageDto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val dao: CharacterDao
) : CharacterRepository {

    override fun fetchCharacter(
        id: Long
    ): Flow<Response<Character>> = flow {
        emit(Loading)
        try {
            val updatedEntity = syncLocalCharacterAndGet(id)
            emit(Success(transformToCharacter(updatedEntity)))
        } catch (e: IOException) {
            emit(getCharacterResponseOnFailure(id, e))
        } catch (e: HttpException) {
            emit(getCharacterResponseOnFailure(id, e))
        }
    }

    private suspend fun syncLocalCharacterAndGet(id: Long): CharacterEntity {
        val dto = api.getCharacter(id)
        val entity = transformToCharacterEntity(dto)
        dao.upsert(entity)
        return entity
    }

    private suspend fun getCharacterResponseOnFailure(
        id: Long,
        e: Exception
    ): Response<Character> {
        val entity = dao.get(id)
        return if (entity == null) {
            Failure(e)
        } else {
            Backup(e, transformToCharacter(entity))
        }
    }

    override fun searchCharacters(
        name: String,
        page: Int
    ): Flow<Response<Page<ImmutableList<Character>>>> = flow {
        emit(Loading)
        try {
            val dto = api.searchCharacters(name, page)
            val entityResults = syncLocalCharactersAndGet(dto)
            emit(Success(transformToCharactersPage(dto, page, entityResults)))
        } catch (e: IOException) {
            emit(Failure(e))
        } catch (e: HttpException) {
            emit(Failure(e))
        }
    }

    private suspend fun syncLocalCharactersAndGet(
        dto: CharactersPageDto
    ): List<CharacterEntity> {
        val entityResults = dto.results.map(::transformToCharacterEntity)
        dao.upsert(entityResults)
        return entityResults
    }
}