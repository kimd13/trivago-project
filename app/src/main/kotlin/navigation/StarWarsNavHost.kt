package com.kimd13.starwarssampleapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kimd13.characterprofilepresentation.navigation.characterProfileScreen
import com.kimd13.characterprofilepresentation.navigation.navigateToCharacterProfileScreen
import com.kimd13.homepresentation.navigation.HOME_ROUTE
import com.kimd13.homepresentation.navigation.homeScreen

@Composable
internal fun StarWarsNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(
            onCharacterClick = navController::navigateToCharacterProfileScreen
        )
        characterProfileScreen()
    }
}