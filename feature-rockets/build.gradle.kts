plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-kapt")
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

    implementation(project(":core-model"))
    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-common"))

    // Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.toolingPreview)
    implementation(Dependencies.Compose.paging)
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.viewModel)
    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.util)
    implementation(Dependencies.Compose.navigation)

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
    implementation(Dependencies.Hilt.navigation)
}