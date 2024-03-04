package com.kimd13.characterprofilepresentation.viewmodel.mapper.homeWorld

import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.homeworld.HomeWorldSection
import com.kimd13.planetcore.model.Planet

/**
 * [Planet] domain model mapping to [HomeWorldSection] UI model.
 */
internal fun transformToHomeWorldSection(
    fromPlanet: Planet
): HomeWorldSection = with(fromPlanet) {
    HomeWorldSection(
        info = HomeWorldInfo(
            name = name,
            population = population
        )
    )
}