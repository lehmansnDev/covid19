plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"
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
            kotlinCompilerExtensionVersion = "1.0.1"
        }
    }
}


dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("androidx.core:core-ktx:1.6.0")

    // COMPOSE
    implementation("androidx.compose.ui:ui:1.1.0-alpha03")
    implementation("androidx.compose.material:material:1.1.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling:1.1.0-alpha03")

    // COMPOSE ACTIVITY
    implementation("androidx.activity:activity-compose:1.3.1")

    // NAVIGATION (important: not 2.4.0-aplpha08)
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha08")

    // ACCOMPANIST
    implementation("com.google.accompanist:accompanist-insets:0.18.0")
    // COIL
    implementation("io.coil-kt:coil-compose:1.3.2")

    // KOIN
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-android-ext:3.0.1-beta-1")
    implementation("io.insert-koin:koin-androidx-workmanager:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.1.2")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$0.2.1")

}