package com.kimd13.characterprofilepresentation.view.characterPersonal

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kimd13.charactercore.model.BirthYear
import com.kimd13.charactercore.model.FeetInches
import com.kimd13.charactercore.model.Height
import com.kimd13.charactercore.model.RecordedBirthYearUnit.BBY
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Loading
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.NothingToDisplay
import com.kimd13.characterprofilepresentation.viewmodel.model.CharacterProfileSectionState.Success
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalInfo
import com.kimd13.characterprofilepresentation.viewmodel.model.characterpersonal.CharacterPersonalSection
import com.kimd13.design.theme.StarWarsTheme
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class CharacterPersonalSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyStateTransitionToNothingToDisplay() {
        // GIVEN

        // Start off in Loading state
        var state: CharacterProfileSectionState<CharacterPersonalSection>
                by mutableStateOf(Loading)

        // Section is launched
        composeTestRule.setContent { TestCharacterPersonalSection(state) }

        // WHEN
        state = NothingToDisplay()

        // THEN
        composeTestRule.waitUntilExactlyOneExists(
            hasText("No intel available", substring = true)
        )
    }

    @Test
    fun verifyStateTransitionToSuccess() {
        // GIVEN

        // Start off in Loading state
        var state: CharacterProfileSectionState<CharacterPersonalSection>
                by mutableStateOf(Loading)

        // Section is launched
        composeTestRule.setContent { TestCharacterPersonalSection(state) }

        // WHEN

        // State transitions to Success with the data below
        val name = "Luke Skywalker"
        val birthYear = BirthYear.Recorded(19F, BBY)
        val height = Height.Recorded(172, FeetInches(5, 8))
        state = Success(
            section = CharacterPersonalSection(
                info = CharacterPersonalInfo(
                    name = name,
                    birthYear = birthYear,
                    height = height
                )
            )
        )

        // THEN

        // Assert that name is found
        composeTestRule.onNodeWithText("Name:", substring = true)
            .assertTextContains(name, substring = true)

        // Assert that all fields of birth year are found
        composeTestRule.onNodeWithText("Birth year:", substring = true)
            .assertTextContains(birthYear.year.toString(), substring = true)
            .assertTextContains(birthYear.unit.name, substring = true)

        // Assert that all fields of height are found
        composeTestRule.onNodeWithText("Height:", substring = true)
            .assertTextContains(height.inCentimeters.toString(), substring = true)
            .assertTextContains(height.inFeetInches.feet.toString(), substring = true)
            .assertTextContains(height.inFeetInches.inches.toString(), substring = true)
    }

    @Composable
    private fun TestCharacterPersonalSection(
        state: CharacterProfileSectionState<CharacterPersonalSection>
    ) {
        StarWarsTheme {
            LazyColumn {
                characterPersonalSection(
                    state = state
                )
            }
        }
    }
}