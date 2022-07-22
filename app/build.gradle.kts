plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
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

    // Accompanist
    implementation(Dependencies.Compose.Accompanist.pager)
    implementation(Dependencies.Compose.Accompanist.pagerIndicator)
    implementation(Dependencies.Compose.Accompanist.systemUiController)

    // Network
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.okLog)
    implementation(Dependencies.gson)

    // Coil Image
    implementation(Dependencies.coil)

    // Koin
    implementation(Dependencies.Koin.koin)
    implementation(Dependencies.Koin.compose)

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