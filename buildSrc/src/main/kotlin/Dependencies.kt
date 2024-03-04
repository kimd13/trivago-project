object Dependencies {

    internal object Versions {
        const val IMMUTABLE_COLLECTIONS = "0.3.6"
        const val COROUTINES = "1.7.2"
        const val ACTIVITY_COMPOSE = "1.8.2"
        const val LIFECYCLE = "2.7.0"
        const val COMPOSE_BOM = "2023.10.01"
        const val NAVIGATION = "2.7.7"
        const val HILT = "2.48.1"
        const val HILT_NAVIGATION = "1.0.0"
        const val RETROFIT = "2.9.0"
        const val ROOM = "2.6.1"
    }

    const val IMMUTABLE_COLLECTIONS =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.IMMUTABLE_COLLECTIONS}"
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val ACTIVITY_COMPOSE =
        "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL_COMPOSE =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}"
    const val LIFECYCLE_COMPOSE =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation"
    const val COMPOSE_UI = "androidx.compose.ui:ui"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAVIGATION}"
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
}