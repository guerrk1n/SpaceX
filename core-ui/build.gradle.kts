plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compiler
    }
}

dependencies {

    implementation(project(":core-designsystem"))

    // Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.toolingPreview)
    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.util)

    // Coil Image
    implementation(Dependencies.coil)

    // Logs
    implementation(Dependencies.timber)

    // Accompanist
    implementation(Dependencies.Compose.Accompanist.pager)

}