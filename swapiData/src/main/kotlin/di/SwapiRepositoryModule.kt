package com.kimd13.swapidata.di

import com.kimd13.charactercore.repository.CharacterRepository
import com.kimd13.filmcore.repository.FilmRepository
import com.kimd13.planetcore.repository.PlanetRepository
import com.kimd13.speciescore.repository.SpeciesRepository
import com.kimd13.swapidata.character.CharacterRepositoryImpl
import com.kimd13.swapidata.film.FilmRepositoryImpl
import com.kimd13.swapidata.planet.PlanetRepositoryImpl
import com.kimd13.swapidata.species.SpeciesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SwapiRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindFilmRepository(
        impl: FilmRepositoryImpl
    ): FilmRepository

    @Binds
    @Singleton
    abstract fun bindPlanetRepository(
        impl: PlanetRepositoryImpl
    ): PlanetRepository

    @Binds
    @Singleton
    abstract fun bindSpeciesRepository(
        impl: SpeciesRepositoryImpl
    ): SpeciesRepository
}