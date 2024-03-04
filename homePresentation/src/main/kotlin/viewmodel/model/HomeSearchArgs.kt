package com.kimd13.homepresentation.viewmodel.model

/**
 * Encapsulates all fields related to a search.
 * Fields must be packaged together to avoid
 * race conditions for creating separate state flows
 * for each field.
 *
 * e.g. Current query is "a" and current page is 2 then
 *      the users searches for "ab" which updates the query and
 *      page resets to 1. Race condition happens if page or query
 *      triggers an emission prior to the other.
 */
data class HomeSearchArgs(
    val query: String,
    val page: Int
)