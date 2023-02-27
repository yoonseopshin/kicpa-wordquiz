plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    implementation(libs.gms.play.services.ads)

    implementation("com.google.android.play:core:1.10.3")
    implementation("com.google.android.play:core-ktx:1.8.1")

    implementation("androidx.recyclerview:recyclerview:1.3.0-rc01")

    implementation(libs.compose.material)
}
