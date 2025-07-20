import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.core)
    alias(libs.plugins.android.kapt)
    alias(libs.plugins.kotlin.serialization)
}

val privateProperties = Properties().apply {
    try {
        load(rootDir.resolve("private.properties").inputStream())
    } catch (e: FileNotFoundException) {
        // Ability to create a debug build without a private.properties file
        Properties()
    }
}

val signingKeystorePassword: String = privateProperties.getProperty("signing.keystore.password", "")
val signingKeyPassword: String = privateProperties.getProperty("signing.key.password", "")

android {
    namespace = "zenith.apps.rategap"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "zenith.apps.rategap"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    signingConfigs {
        register("release") {
            storeFile = file("../keystore.jks")
            storePassword = signingKeystorePassword
            keyAlias = "key"
            keyPassword = signingKeyPassword
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["release"]
        }
        debug {
            applicationIdSuffix = ".deubg"
            versionNameSuffix = "debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:network"))
    implementation(project(":feature:screen:rate"))
    implementation(project(":feature:screen:splash"))
    implementation(project(":feature:screen:pick-currency"))
    implementation(project(":feature:storage"))
    implementation(project(":feature:domain:currency"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.core)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.kapt)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.splashscreen)
}