plugins {
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation(libs.hilt.work)
    kapt(libs.hilt.work.compiler)
    implementation(libs.androidx.work.runtime.ktx)
}
