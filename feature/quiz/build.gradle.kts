plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.feature.quiz"
}

dependencies {
    releaseImplementation(libs.gms.play.services.ads)
    implementation(libs.google.play.inappreview.ktx)
}
