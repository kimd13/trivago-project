package com.kimd13.core

data class Page<T>(
    val currentPageNumber: Int,
    val nextPageNumber: Int?,
    val previousPageNumber: Int?,
    val results: T
)