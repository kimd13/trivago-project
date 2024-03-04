package com.kimd13.core

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Transforms given iterable into a immutable list.
 */
fun <T, R> Iterable<T>.mapImmutable(transform: (T) -> R): ImmutableList<R> =
    this.map(transform).toImmutableList()