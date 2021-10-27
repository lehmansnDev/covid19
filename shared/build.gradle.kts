import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.squareup.sqldelight")
    kotlin("kapt")
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Ktor.core)
                implementation(Ktor.logging)
                implementation(Ktor.json)
                implementation(Ktor.serialization)

                implementation(Kotlinx.serialization)
                implementation(Kotlinx.coroutines)
                implementation(Kotlinx.datetime)

                implementation(SqlDelight.runtime)

                implementation(Koin.core)

                api(Lighthouse.logging)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.android)
                implementation(SqlDelight.android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Ktor.ios)
                implementation(SqlDelight.native)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 31
    }
}

sqldelight {
    database("LocalDB") {
        packageName = "de.simon.covid19.database"
        sourceFolders = listOf("kotlin")
    }
}