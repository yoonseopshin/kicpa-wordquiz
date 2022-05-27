plugins {
    `kotlin-dsl`
}

group = "com.ysshin.cpaquiz.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.spotless.gradle.plugin)
}
