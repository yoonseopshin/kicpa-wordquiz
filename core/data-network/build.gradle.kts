plugins {
    id("kotlinx-serialization")
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    implementation(libs.bundles.retrofit)
}