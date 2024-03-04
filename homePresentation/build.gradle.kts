import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    androidLibrary()
    kotlinAndroid()
    ideaExt()
    ksp()
}

idea.module.settings.packagePrefix.apply {
    set("src/main/kotlin", "com.kimd13.homepresentation")
    set("src/test/kotlin", "com.kimd13.homepresentation")
    set("src/androidTest/kotlin", "com.kimd13.homepresentation")
}

android {
    namespace = "com.kimd13.homepresentation"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = CompilerVersions.COMPOSE
    }
}

dependencies {
    // Includes Compose dependencies.
    implementation(project(":design"))
    implementation(project(":characterCore"))

    with(KspDependencies) {
        ksp(HILT)
    }

    with(Dependencies) {
        implementation(IMMUTABLE_COLLECTIONS)
        implementation(COROUTINES)
        implementation(LIFECYCLE_VIEWMODEL)
        implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
        implementation(LIFECYCLE_COMPOSE)
        implementation(HILT)
        implementation(HILT_NAVIGATION)
    }

    with(TestDependencies) {
        testImplementation(JUNIT)
        testImplementation(MOCKK)
        testImplementation(COROUTINES)
        testImplementation(TURBINE)
    }
}