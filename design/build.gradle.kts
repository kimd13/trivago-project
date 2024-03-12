import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    androidLibrary()
    kotlinAndroid()
    ideaExt()
}

idea.module.settings.packagePrefix.apply {
    set("src/main/kotlin", "com.kimd13.design")
    set("src/androidTest/kotlin", "com.kimd13.design")
}

android {
    namespace = "com.kimd13.design"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = CompilerVersions.COMPOSE
    }
}

dependencies {
    with(Dependencies) {
        api(platform(COMPOSE_BOM))
        api(COMPOSE_FOUNDATION)
        api(COMPOSE_UI)
        api(COMPOSE_UI_TOOLING_PREVIEW)
        api(IMMUTABLE_COLLECTIONS)

        // For internal usage only.
        implementation(CORE_KTX)
        implementation(COMPOSE_MATERIAL_3)
    }

    with(DebugDependencies) {
        debugApi(COMPOSE_UI_TOOLING)
        debugApi(COMPOSE_TEST_MANIFEST)
    }
}