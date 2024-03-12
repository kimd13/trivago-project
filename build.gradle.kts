// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    androidApplication() apply false
    kotlinAndroid() apply false
    androidLibrary() apply false
    ksp() apply false
    hilt() apply false
}

// Generates compose reports by running
// ./gradlew assembleRelease -PcomposeCompilerReports=true
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.projectDir.absolutePath}/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.projectDir.absolutePath}/compose_compiler"
                )
            }
        }
    }
}