plugins {
    id("kotlinx-serialization")
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.core.database"
}

dependencies {
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
}
