plugins {
    id("kotlinx-serialization")
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.core.network"
}

dependencies {
    implementation(libs.bundles.retrofit)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker)
}
