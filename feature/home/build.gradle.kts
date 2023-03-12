plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    implementation(libs.gms.play.services.ads)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.google.play.core)
    implementation(libs.google.play.core.ktx)
}
