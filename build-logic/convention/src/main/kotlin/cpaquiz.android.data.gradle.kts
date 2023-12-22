import com.ysshin.cpaquiz.configureKotlinAndroid
import com.ysshin.cpaquiz.libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    configureKotlinAndroid(this)

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:android"))
    implementation(project(":core:domain"))

    add("implementation", libs.findLibrary("hilt.android").get())
    add("kapt", libs.findLibrary("hilt.compiler").get())

    add("implementation", libs.findLibrary("coroutines-android").get())
    add("implementation", libs.findLibrary("coroutines-test").get())

    add("implementation", libs.findLibrary("retrofit-kotlinx-serialization").get())
    add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findBundle("androidx-test").get())
}
