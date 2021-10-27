plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "de.simon.covid19.android"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.1.0-alpha04"
        }
    }
}


dependencies {
    implementation(project(":shared"))

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")

    implementation(Compose.ui)
    implementation(Compose.uiTooling)
    implementation(Compose.material)
    implementation(Compose.constraintLayout)
    implementation(Compose.navigation)
    implementation(Compose.activity)
    implementation(Compose.coil)

    implementation(Accompanist.insets)

    implementation(Koin.android)
    implementation(Koin.ext)
    implementation(Koin.compose)

    implementation(Kotlinx.datetime)
}