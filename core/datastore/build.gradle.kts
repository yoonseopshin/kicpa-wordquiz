plugins {
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
}

android {
    namespace = "com.ysshin.cpaquiz.core.datastore"
}

dependencies {
    implementation(libs.datastore)
}
