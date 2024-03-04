package com.kimd13.homepresentation.viewmodel.model

import com.kimd13.charactercore.model.Character
import com.kimd13.core.Page
import com.kimd13.core.Response
import kotlinx.collections.immutable.ImmutableList

/**
 * Intermediate data model which wraps the domain response
 * with the associated search args used to fetch it.
 * This is needed because some screen UI models need
 * this information.
 */
data class PageResponseWithArgs(
    val response: Response<Page<ImmutableList<Character>>>,
    val searchArgs: HomeSearchArgs
)