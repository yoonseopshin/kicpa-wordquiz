plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")
}