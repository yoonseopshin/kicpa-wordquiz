plugins {
    id("kotlinx-serialization")
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

android {
    namespace = "com.ysshin.cpaquiz.core.network"
}

dependencies {
    implementation(libs.bundles.retrofit)
}
