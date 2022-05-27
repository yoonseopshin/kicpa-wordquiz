plugins {
    `kotlin-dsl`
}

group = "com.ysshin.cpaquiz.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.spotless.gradle.plugin)
}
