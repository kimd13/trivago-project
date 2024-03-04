package com.kimd13.speciescore.repository

import com.kimd13.core.Response
import com.kimd13.speciescore.model.Species
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface SpeciesRepository {

    fun fetchSpecies(
        id: Long
    ): Flow<Response<Species>>

    fun fetchSpecies(
        ids: ImmutableList<Long>
    ): Flow<Response<ImmutableList<Species>>>
}