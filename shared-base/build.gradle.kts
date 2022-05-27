plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("cpaquiz.spotless")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api(libs.coroutines.core)
}