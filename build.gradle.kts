// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://storage.googleapis.com/r8-releases/raw") }
    }

    dependencies {
        classpath(libs.android.r8)
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.spotless.gradle.plugin)
        classpath(libs.oss.licenses.plugin)
        classpath(libs.gms.google.services.plugin)
        classpath(libs.androidx.benchmark.baseline.profile.gradle.plugin)
        classpath(libs.gradle.dependency.graph.generator.plugin)
    }
}

apply(plugin = "com.vanniktech.dependency.graph.generator")
