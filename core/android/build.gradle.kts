plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("cpaquiz.spotless")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    namespace = "com.ysshin.cpaquiz.core.android"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "APP_VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
        buildConfigField("String", "APP_VERSION_CODE", "\"${libs.versions.versionCode.get()}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    api(libs.bundles.androidx.shared)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)
    androidTestApi(libs.compose.ui.test)
    debugApi(libs.compose.ui.tooling)

    debugApi(libs.androidx.customview)
    debugApi(libs.androidx.customview.poolingcontainer)

    api(libs.androidx.activity.compose)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api("androidx.lifecycle:lifecycle-extensions:2.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.accompanist.flowlayout)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(libs.coroutines.android)
    api(libs.timber)

    releaseImplementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    releaseImplementation(libs.gms.play.services.ads)
}
