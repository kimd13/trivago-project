package com.kimd13.characterprofilepresentation.viewmodel.model

import androidx.compose.runtime.Immutable
import com.kimd13.design.component.StarWarsWarning

@Immutable
sealed interface CharacterProfileSectionState<out T> {

    sealed interface CharacterProfileSectionStateWithWarning<out T> :
        CharacterProfileSectionState<T> {
        val warning: StarWarsWarning?
    }

    data class Success<out T>(
        val section: T,
        override val warning: StarWarsWarning? = null
    ) : CharacterProfileSectionStateWithWarning<T>

    data class NothingToDisplay(
        override val warning: StarWarsWarning? = null
    ) : CharacterProfileSectionStateWithWarning<Nothing>

    data object Loading : CharacterProfileSectionState<Nothing>
}