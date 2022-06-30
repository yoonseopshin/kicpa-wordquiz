plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("cpaquiz.spotless")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared-android"))
    implementation(project(":shared-base"))
    implementation(project(":domain"))

    implementation(libs.bundles.retrofit)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    debugImplementation("com.facebook.flipper:flipper:0.145.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.3")
    debugImplementation("com.facebook.flipper:flipper-network-plugin:0.145.0")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.145.0")
    releaseImplementation("com.github.theGlenn:flipper-android-no-op:0.9.0")
}