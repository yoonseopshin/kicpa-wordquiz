plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
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
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    api(libs.bundles.androidx.shared)
    api(libs.material)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)
    androidTestApi(libs.compose.ui.test)
    debugApi(libs.compose.ui.tooling)

    debugApi("androidx.customview:customview:1.2.0-alpha01")
    debugApi(libs.androidx.customview.poolingcontainer)

    api(libs.androidx.activity.compose)
    api("androidx.navigation:navigation-compose:2.5.1")
    api("androidx.hilt:hilt-navigation-compose:1.0.0")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    api("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01")
    api("androidx.lifecycle:lifecycle-extensions:2.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.accompanist.flowlayout)

    implementation(libs.coroutines.android)
    api(libs.timber)

    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("com.google.android.gms:play-services-ads:20.6.0")
    implementation("com.google.android.play:core:1.10.3")
    implementation("com.google.android.play:core-ktx:1.8.1")
}
