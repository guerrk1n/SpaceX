import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.androidBuildPlugin)
        classpath(Dependencies.Gradle.kotlinPlugin)
    }
}

plugins {
    id(Dependencies.Gradle.VersionsPlugin.id) version Dependencies.Gradle.VersionsPlugin.version
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "plugins.ktlint")
    afterEvaluate {
        tasks.withType(KotlinCompile::class).all {
            kotlinOptions {
                jvmTarget = Config.javaVersion.toString()
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
            }
        }

        extensions.findByType<BaseExtension>() ?: return@afterEvaluate
        configure<BaseExtension> {
            compileSdkVersion(Config.compileSdk)
            defaultConfig {
                minSdk = Config.minSdk
                targetSdk = Config.targetSdk
                versionName = Config.versionName
                versionCode = Config.versionCode
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = Config.javaVersion
                targetCompatibility = Config.javaVersion
            }
        }
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    checkForGradleUpdate = true
    gradleReleaseChannel = GradleReleaseChannel.RELEASE_CANDIDATE.id
    revision = "integration"
    outputFormatter = "plain"
    outputDir = "build/dependencyUpdates"
    reportfileName = "dependency_update_report"
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}