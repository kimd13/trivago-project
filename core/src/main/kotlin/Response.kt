package com.kimd13.core

/**
 * Wrapper class to facilitate mapping from the domain layer upwards.
 */
sealed interface Response<out T> {

    sealed interface ResponseWithData<out T> : Response<T> {
        val data: T
    }

    sealed interface ResponseWithoutData : Response<Nothing>

    data class Success<out T>(
        override val data: T
    ) : ResponseWithData<T>

    data class Backup<out T>(
        val exception: Exception,
        override val data: T
    ) : ResponseWithData<T>

    data class Failure(
        val exception: Exception
    ) : ResponseWithoutData

    data object Loading : ResponseWithoutData
}