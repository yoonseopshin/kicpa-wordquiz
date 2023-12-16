plugins {
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.sync"
}

dependencies {
    implementation(libs.hilt.work)
    kapt(libs.hilt.work.compiler)
    implementation(libs.androidx.work.runtime.ktx)
}
