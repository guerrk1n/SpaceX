object Dependencies {

    const val ktlint = "com.pinterest:ktlint:0.46.1"
    const val coil = "io.coil-kt:coil-compose:2.1.0"
    const val gson = "com.google.code.gson:gson:2.9.1"
    const val timber = "com.jakewharton.timber:timber:5.0.1"

    object Kotlin {
        const val version = "1.7.0"
    }

    object Hilt {
        const val version = "2.43.2"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object OkHttp {
        const val okHttp = "com.squareup.okhttp3:okhttp:4.10.0"
        const val okLog = "com.github.simonpercic:oklog3:2.3.0"
    }

    object Gradle {
        const val androidBuildPlugin = "com.android.tools.build:gradle:7.2.2"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

        object VersionsPlugin {
            const val id = "com.github.ben-manes.versions"
            const val version = "0.42.0"
        }
    }

    object Room {
        const val version = "2.4.3"
        const val ktx = "androidx.room:room-ktx:$version"
        const val runtime = "androidx.room:room-runtime:$version"
        const val kapt = "androidx.room:room-compiler:$version"
        const val paging = "androidx.room:room-paging:$version"
    }

    object AndroidX {
        object Ktx {
            const val core = "androidx.core:core-ktx:1.8.0"
        }
    }

    object Compose {
        const val version = "1.2.0"
        const val compiler = "1.2.0"
        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val paging = "androidx.paging:paging-compose:1.0.0-alpha15"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-rc02"
        const val activity = "androidx.activity:activity-compose:1.5.0"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val util = "androidx.compose.ui:ui-util:$version"
        const val navigation = "androidx.navigation:navigation-compose:2.5.1"

        object Accompanist {
            private const val version = "0.25.0"

            const val pager = "com.google.accompanist:accompanist-pager-indicators:$version"
            const val pagerIndicator = "com.google.accompanist:accompanist-pager:$version"
            const val systemUiController =
                "com.google.accompanist:accompanist-systemuicontroller:$version"
        }
    }

    object Retrofit {
        const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Pluto {
        const val version = "2.0.4"

        object Debug {
            const val pluto = "com.plutolib:pluto:$version"
            const val network = "com.plutolib.plugins:network:$version"
            const val exceptions = "com.plutolib.plugins:exceptions:$version"

        }

        object Release {
            const val pluto = "com.plutolib:pluto-no-op:$version"
            const val network = "com.plutolib.plugins:network-no-op:$version"
            const val exceptions = "com.plutolib.plugins:exceptions-no-op:$version"
        }
    }
}