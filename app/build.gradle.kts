import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    androidApplication()
    kotlinAndroid()
    ideaExt()
    ksp()
    hilt()
}

idea.module.settings.packagePrefix.apply {
    set("src/main/kotlin", "com.kimd13.starwarssampleapp")
    set("src/test/kotlin", "com.kimd13.starwarssampleapp")
    set("src/androidTest/kotlin", "com.kimd13.starwarssampleapp")
}

android {
    namespace = "com.kimd13.starwarssampleapp"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.kimd13.starwarssampleapp"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":design"))
    implementation(project(":homePresentation"))
    implementation(project(":characterProfilePresentation"))
    implementation(project(":swapiData"))

    with(KspDependencies) {
        ksp(HILT)
    }

    with(Dependencies) {
        implementation(ACTIVITY_COMPOSE)
        implementation(NAVIGATION_COMPOSE)
        implementation(HILT)
        implementation(HILT_NAVIGATION)
    }
}