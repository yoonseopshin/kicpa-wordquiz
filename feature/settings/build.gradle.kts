plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.feature.settings"
}

dependencies {
    implementation(libs.play.services.oss.licenses)
}
