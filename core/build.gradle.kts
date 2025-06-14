plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.core)
    alias(libs.plugins.android.kapt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "zenith.apps.core"
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.hilt.core)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.kapt)
    implementation(libs.kotlinx.serialization.json)
}