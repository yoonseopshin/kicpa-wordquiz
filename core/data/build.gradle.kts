plugins {
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

android {
    namespace = "com.ysshin.cpaquiz.core.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
}
