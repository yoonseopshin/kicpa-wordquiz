plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        buildConfigField("String", "APP_VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
        buildConfigField("String", "APP_VERSION_CODE", "\"${libs.versions.versionCode.get()}\"")

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

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

}

dependencies {
    api("androidx.navigation:navigation-compose:2.5.1")
    api("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}