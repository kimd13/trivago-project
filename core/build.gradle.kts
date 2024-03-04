import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    androidLibrary()
    kotlinAndroid()
    ideaExt()
}

idea.module.settings.packagePrefix.apply {
    set("src/main/kotlin", "com.kimd13.core")
    set("src/test/kotlin", "com.kimd13.core")
}

android {
    namespace = "com.kimd13.core"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    with(Dependencies) {
        // Considered extensions to std lib and foundational for this project.
        // Any module depending on :core will have access to these deps.
        api(IMMUTABLE_COLLECTIONS)
        api(COROUTINES)
    }
}