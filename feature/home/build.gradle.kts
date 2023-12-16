plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.feature.home"
}

dependencies {
    releaseImplementation(libs.gms.play.services.ads)
    implementation(libs.accompanist.flowlayout)
}
