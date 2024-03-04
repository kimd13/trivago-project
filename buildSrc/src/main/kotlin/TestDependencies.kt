object TestDependencies {
    internal object Versions {
        const val JUNIT = "4.13.2"
        const val MOCKK = "1.13.9"
        const val COROUTINES = "1.7.2"
        const val TURBINE = "1.0.0"
    }

    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    const val TURBINE = "app.cash.turbine:turbine:${Versions.TURBINE}"
}