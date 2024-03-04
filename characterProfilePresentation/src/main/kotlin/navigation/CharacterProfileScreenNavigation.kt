package com.kimd13.characterprofilepresentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kimd13.characterprofilepresentation.view.CharacterProfileScreen
import com.kimd13.characterprofilepresentation.viewmodel.CharacterProfileViewModel

private const val CHARACTER_PROFILE_BASE_ROUTE = "character_profile"
internal const val CHARACTER_PROFILE_ID_ARG = "id"
const val CHARACTER_PROFILE_ROUTE = "${CHARACTER_PROFILE_BASE_ROUTE}/{${CHARACTER_PROFILE_ID_ARG}}"

fun NavGraphBuilder.characterProfileScreen() {
    composable(
        CHARACTER_PROFILE_ROUTE,
        arguments = listOf(
            navArgument(CHARACTER_PROFILE_ID_ARG) {
                type = NavType.LongType
            }
        )
    ) {
        val viewModel: CharacterProfileViewModel = hiltViewModel()
        val characterPersonalSectionState by remember {
            viewModel.characterPersonalSectionState
        }.collectAsStateWithLifecycle()
        val speciesSectionState by remember {
            viewModel.speciesSectionState
        }.collectAsStateWithLifecycle()
        val homeWorldSectionState by remember {
            viewModel.homeWorldSectionState
        }.collectAsStateWithLifecycle()
        val filmsSectionState by remember {
            viewModel.filmsSectionState
        }.collectAsStateWithLifecycle()

        CharacterProfileScreen(
            characterPersonalSectionState = characterPersonalSectionState,
            speciesSectionState = speciesSectionState,
            homeWorldSectionState = homeWorldSectionState,
            filmsSectionState = filmsSectionState
        )
    }
}

fun NavController.navigateToCharacterProfileScreen(id: Long) {
    navigate("${CHARACTER_PROFILE_BASE_ROUTE}/${id}")
}

internal fun SavedStateHandle.characterProfileId(): Long =
    checkNotNull(get<Long>(CHARACTER_PROFILE_ID_ARG))