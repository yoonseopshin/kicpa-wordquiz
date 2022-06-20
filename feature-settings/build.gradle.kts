plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.play.services.oss.licenses)
}