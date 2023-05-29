plugins {
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

android {
    namespace = "com.ysshin.cpaquiz.core.datastore"
}

dependencies {
    implementation(libs.datastore)
}
