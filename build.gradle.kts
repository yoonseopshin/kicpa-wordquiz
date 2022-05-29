// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.spotless.gradle.plugin)
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.5")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

