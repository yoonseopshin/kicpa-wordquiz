// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
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
        classpath("com.google.gms:google-services:4.3.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

