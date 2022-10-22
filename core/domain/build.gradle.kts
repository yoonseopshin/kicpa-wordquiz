plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.coroutines.core)
}
