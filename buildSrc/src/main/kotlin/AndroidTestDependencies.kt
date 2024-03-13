object AndroidTestDependencies {

    const val COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4"

    // Needed for createAndroidComposeRule (access to Activity), but not createComposeRule.
    const val COMPOSE_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
}