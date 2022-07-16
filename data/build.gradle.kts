plugins {
    id("cpaquiz.android.data")
    id("cpaquiz.spotless")
    id("cpaquiz.versioning")
}

dependencies {
    implementation(project(":data-database"))
    implementation(project(":data-datastore"))
    implementation(project(":data-network"))
}