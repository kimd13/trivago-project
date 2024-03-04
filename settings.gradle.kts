pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Star Wars Sample App"
include(
    ":app",
    ":core",
    ":characterCore",
    ":speciesCore",
    ":filmCore",
    ":planetCore",
    ":swapiData",
    ":design",
    ":homePresentation",
    ":characterProfilePresentation"
)