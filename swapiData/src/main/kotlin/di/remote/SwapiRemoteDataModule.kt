package com.kimd13.swapidata.di.remote

import com.kimd13.swapidata.character.remote.CharacterApi
import com.kimd13.swapidata.film.remote.FilmApi
import com.kimd13.swapidata.planet.remote.PlanetApi
import com.kimd13.swapidata.species.remote.SpeciesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SwapiRemoteDataModule {

    @Provides
    fun provideBaseUrl(): String = "https://swapi.dev/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)

    @Provides
    @Singleton
    fun provideFilmApi(retrofit: Retrofit): FilmApi =
        retrofit.create(FilmApi::class.java)

    @Provides
    @Singleton
    fun providePlanetApi(retrofit: Retrofit): PlanetApi =
        retrofit.create(PlanetApi::class.java)

    @Provides
    @Singleton
    fun provideSpeciesApi(retrofit: Retrofit): SpeciesApi =
        retrofit.create(SpeciesApi::class.java)
}