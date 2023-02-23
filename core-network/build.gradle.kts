plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(project(":core-common"))

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)

    // Network
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.okLog)
    implementation(Dependencies.gson)

    // Logs
    implementation(Dependencies.timber)

    // Pluto
    debugImplementation(Dependencies.Pluto.Debug.network)
    releaseImplementation(Dependencies.Pluto.Release.network)
}