plugins {
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    implementation(project(":core:data-database"))
    implementation(project(":core::data-datastore"))
    implementation(project(":core:data-network"))
}
