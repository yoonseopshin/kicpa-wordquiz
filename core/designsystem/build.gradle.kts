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
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    api(platform(libs.androidx.compose.bom))
    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.compose)
    androidTestApi(libs.compose.ui.test)
    debugApi(libs.compose.ui.tooling)
}
