plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "zenith.apps.storage"
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

    implementation(libs.androidx.core.ktx)
}