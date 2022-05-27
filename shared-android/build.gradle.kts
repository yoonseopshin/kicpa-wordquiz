plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
//    id("cpawordquiz.spotless")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        buildConfigField("String", "APP_VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
        buildConfigField("String", "APP_VERSION_CODE", "\"${libs.versions.versionCode.get()}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

}

dependencies {
    implementation(project(":shared-base"))
    api(libs.bundles.androidx.shared)
    api(libs.material)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    api(libs.bundles.compose)
    androidTestApi(libs.compose.ui.test)
    debugApi(libs.compose.ui.tooling)
    api("androidx.activity:activity-compose:1.4.0")
    api("androidx.navigation:navigation-compose:2.5.0-rc01")
    api("androidx.hilt:hilt-navigation-compose:1.0.0")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")

    api("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    api("androidx.lifecycle:lifecycle-extensions:2.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    api(libs.kotlinx.serialization)

    debugApi("androidx.customview:customview:1.2.0-alpha01")
    debugApi("androidx.customview:customview-poolingcontainer:1.0.0-beta02")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    api(libs.timber)
}