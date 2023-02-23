plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {

    buildTypes {
        getByName("debug") {
            isDefault = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        getByName("release") {
            isShrinkResources = false
            isMinifyEnabled = false
            isDebuggable = false
            //signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compiler
    }

    packagingOptions.apply {
        resources.excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":feature-rockets"))
    implementation(project(":feature-crew"))
    implementation(project(":feature-history-events"))
    implementation(project(":feature-rocket-detail"))
    implementation(project(":feature-launchpads"))
    implementation(project(":feature-launchpad-detail"))

    implementation(project(":core-ui"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-data"))
    implementation(project(":core-common"))
    implementation(project(":core-network"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))


    // AndroidX
    implementation(Dependencies.AndroidX.Ktx.core)

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

    // Accompanist
    implementation(Dependencies.Compose.Accompanist.pager)
    implementation(Dependencies.Compose.Accompanist.pagerIndicator)
    implementation(Dependencies.Compose.Accompanist.systemUiController)

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
    implementation(Dependencies.Hilt.navigation)

    // Logs
    implementation(Dependencies.timber)

    // Debugging
    debugImplementation(Dependencies.Pluto.Debug.pluto)
    debugImplementation(Dependencies.Pluto.Debug.network)
    debugImplementation(Dependencies.Pluto.Debug.exceptions)
    releaseImplementation(Dependencies.Pluto.Release.pluto)
    releaseImplementation(Dependencies.Pluto.Release.network)
    releaseImplementation(Dependencies.Pluto.Release.exceptions)

}