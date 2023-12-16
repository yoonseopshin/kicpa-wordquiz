plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("cpaquiz.spotless")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.coroutines.core)
}
