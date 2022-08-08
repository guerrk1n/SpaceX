plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {

    implementation(project(":core-common"))


    // Network
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.okLog)
    implementation(Dependencies.gson)

    // Pluto
    debugImplementation(Dependencies.Pluto.Debug.network)
    releaseImplementation(Dependencies.Pluto.Release.network)
}