import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    androidLibrary()
    kotlinAndroid()
    ideaExt()
    ksp()
}

idea.module.settings.packagePrefix.apply {
    set("src/main/kotlin", "com.kimd13.swapidata")
    set("src/test/kotlin", "com.kimd13.swapidata")
    set("src/androidTest/kotlin", "com.kimd13.swapidata")
}

android {
    namespace = "com.kimd13.swapidata"
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
    implementation(project(":characterCore"))
    implementation(project(":speciesCore"))
    implementation(project(":planetCore"))
    implementation(project(":filmCore"))

    with(KspDependencies) {
        ksp(HILT)
        ksp(ROOM)
    }

    with(Dependencies) {
        implementation(IMMUTABLE_COLLECTIONS)
        implementation(COROUTINES)
        implementation(HILT)
        implementation(RETROFIT)
        implementation(RETROFIT_GSON)
        implementation(ROOM)
        implementation(ROOM_KTX)
    }

    with(TestDependencies) {
        testImplementation(JUNIT)
        testImplementation(MOCKK)
        testImplementation(COROUTINES)
        testImplementation(TURBINE)
    }
}