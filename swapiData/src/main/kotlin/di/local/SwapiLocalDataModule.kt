package com.kimd13.swapidata.di.local

import android.content.Context
import androidx.room.Room
import com.kimd13.swapidata.SwapiDatabase
import com.kimd13.swapidata.character.local.CharacterDao
import com.kimd13.swapidata.film.local.FilmDao
import com.kimd13.swapidata.planet.local.PlanetDao
import com.kimd13.swapidata.species.local.SpeciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SwapiLocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SwapiDatabase {
        return Room.databaseBuilder(
            appContext,
            SwapiDatabase::class.java,
            "SwapiDatabase"
        ).build()
    }

    @Provides
    fun provideCharacterDao(database: SwapiDatabase): CharacterDao =
        database.characterDao()

    @Provides
    fun provideFilmDao(database: SwapiDatabase): FilmDao =
        database.filmDao()

    @Provides
    fun providePlanetDao(database: SwapiDatabase): PlanetDao =
        database.planetDao()

    @Provides
    fun provideSpeciesDao(database: SwapiDatabase): SpeciesDao =
        database.speciesDao()
}