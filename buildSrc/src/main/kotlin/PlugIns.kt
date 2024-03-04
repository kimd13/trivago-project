import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

internal object PlugIns {
    internal object Versions {
        internal const val ANDROID_APPLICATION = "8.1.4"
        internal const val KOTLIN_ANDROID = "1.9.22"
        internal const val ANDROID_LIBRARY = "8.1.4"
        internal const val IDEA_EXT = "1.1.7"
        internal const val PARCELIZE = "1.9.22"
        internal const val KSP = "1.9.22-1.0.16"
        internal const val HILT = "2.48.1"
    }

    internal const val ANDROID_APPLICATION = "com.android.application"
    internal const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    internal const val ANDROID_LIBRARY = "com.android.library"
    internal const val IDEA_EXT = "org.jetbrains.gradle.plugin.idea-ext"
    internal const val PARCELIZE = "kotlin-parcelize"
    internal const val KSP = "com.google.devtools.ksp"
    internal const val HILT = "com.google.dagger.hilt.android"
}

fun PluginDependenciesSpec.androidApplication(): PluginDependencySpec =
    id(PlugIns.ANDROID_APPLICATION) version PlugIns.Versions.ANDROID_APPLICATION

fun PluginDependenciesSpec.kotlinAndroid(): PluginDependencySpec =
    id(PlugIns.KOTLIN_ANDROID) version PlugIns.Versions.KOTLIN_ANDROID

fun PluginDependenciesSpec.androidLibrary(): PluginDependencySpec =
    id(PlugIns.ANDROID_LIBRARY) version PlugIns.Versions.ANDROID_LIBRARY

fun PluginDependenciesSpec.ideaExt(): PluginDependencySpec =
    id(PlugIns.IDEA_EXT) version PlugIns.Versions.IDEA_EXT

fun PluginDependenciesSpec.parcelize(): PluginDependencySpec =
    id(PlugIns.PARCELIZE) version PlugIns.Versions.PARCELIZE

fun PluginDependenciesSpec.ksp(): PluginDependencySpec =
    id(PlugIns.KSP) version PlugIns.Versions.KSP

fun PluginDependenciesSpec.hilt(): PluginDependencySpec =
    id(PlugIns.HILT) version PlugIns.Versions.HILT

