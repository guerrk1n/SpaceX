plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(":core-network"))
    implementation(project(":core-database"))
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    // Room
    implementation(Dependencies.Room.ktx)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.paging)
    kapt(Dependencies.Room.kapt)

    // Paging
    implementation(Dependencies.Compose.paging)

    // Retrofit
    implementation(Dependencies.Retrofit.retrofit)

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
}