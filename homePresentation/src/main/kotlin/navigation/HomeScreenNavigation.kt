package com.kimd13.homepresentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kimd13.homepresentation.view.HomeScreen
import com.kimd13.homepresentation.viewmodel.HomeViewModel

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    onCharacterClick: (id: Long) -> Unit
) {
    composable(HOME_ROUTE) {
        val viewModel: HomeViewModel = hiltViewModel()
        val searchArgs by remember {
            viewModel.searchArgs
        }.collectAsStateWithLifecycle()
        val screenState by remember {
            viewModel.screenState
        }.collectAsStateWithLifecycle()

        HomeScreen(
            state = screenState,
            searchArgs = searchArgs,
            searchCharacters = viewModel::searchCharacters,
            onCharacterClick = onCharacterClick
        )
    }
}