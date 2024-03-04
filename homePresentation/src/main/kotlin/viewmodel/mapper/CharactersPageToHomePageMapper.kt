package com.kimd13.homepresentation.viewmodel.mapper

import com.kimd13.charactercore.model.Character
import com.kimd13.core.Page
import com.kimd13.core.mapImmutable
import com.kimd13.homepresentation.viewmodel.model.HomePage
import kotlinx.collections.immutable.ImmutableList

internal fun transformToHomePage(
    page: Page<ImmutableList<Character>>,
    nextPage: () -> Unit,
    previousPage: () -> Unit
): HomePage {
    val nextPageExists = page.nextPageNumber != null
    val safeNextPage = safelyWrapPageAction(nextPageExists, nextPage)
    val previousPageExists = page.previousPageNumber != null
    val safePreviousPage = safelyWrapPageAction(previousPageExists, previousPage)

    return HomePage(
        number = page.currentPageNumber,
        nextPageExists = nextPageExists,
        nextPage = safeNextPage,
        previousPageExists = previousPageExists,
        previousPage = safePreviousPage,
        characters = page.results.mapImmutable(::transformToHomeCharacter)
    )
}

/**
 * Given a page does not exist, return a empty function
 * which prevents [pageAction] from being invoked mistakenly.
 */
private fun safelyWrapPageAction(
    pageExits: Boolean,
    pageAction: () -> Unit
): () -> Unit =
    if (pageExits) {
        pageAction
    } else {
        {}
    }