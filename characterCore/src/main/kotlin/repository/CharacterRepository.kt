package com.kimd13.charactercore.repository

import com.kimd13.charactercore.model.Character
import com.kimd13.core.Page
import com.kimd13.core.Response
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun fetchCharacter(
        id: Long
    ): Flow<Response<Character>>

    fun searchCharacters(
        name: String,
        page: Int
    ): Flow<Response<Page<ImmutableList<Character>>>>
}