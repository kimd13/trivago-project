package com.kimd13.swapidata.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class SwapiDataDispatcher

@Module
@InstallIn(SingletonComponent::class)
object SwapiDispatcherModule {

    @SwapiDataDispatcher
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}