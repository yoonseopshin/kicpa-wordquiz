// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.spotless.gradle.plugin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.0")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.5")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

