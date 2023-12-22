import com.ysshin.cpaquiz.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("cpaquiz.spotless")
}

android {
    configureKotlinAndroid(this)

    namespace = "com.ysshin.cpaquiz.designsystem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.bundles.androidx.shared)
    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)
    androidTestApi(libs.compose.ui.test)
    debugApi(libs.compose.ui.tooling)
}
