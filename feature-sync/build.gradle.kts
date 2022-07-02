plugins {
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.7.1")
}