object KspDependencies {
    internal object Versions {
        const val HILT = "2.48.1"
        const val ROOM = "2.6.1"
    }

    const val HILT = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val ROOM = "androidx.room:room-compiler:${Versions.ROOM}"
}