plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    releaseImplementation(libs.gms.play.services.ads)
    implementation(libs.accompanist.flowlayout)
}
