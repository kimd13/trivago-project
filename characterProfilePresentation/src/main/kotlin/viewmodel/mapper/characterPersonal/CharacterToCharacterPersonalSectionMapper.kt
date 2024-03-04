package com.kimd13.characterprofilepresentation.viewmodel.mapper.characterPersonal

import com.kimd13.charactercore.model.Character
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection

/**
 * [Character] domain model mapping to [CharacterPersonalSection] UI model.
 */
internal fun transformToCharacterPersonalSection(
    fromCharacter: Character
): CharacterPersonalSection = with(fromCharacter) {
    CharacterPersonalSection(
        info = CharacterPersonalInfo(
            name = name,
            birthYear = birthYear,
            height = height
        )
    )
}