plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.core)
    alias(libs.plugins.android.kapt)
}

android {
    namespace = "zenith.apps.currency"
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmTargetCompatibility.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:network"))
    implementation(project(":feature:storage"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.hilt.core)
    kapt(libs.hilt.kapt)
}