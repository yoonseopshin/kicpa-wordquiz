plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation(project(":nativetemplates"))

    implementation("com.google.android.gms:play-services-ads:20.6.0")

    implementation("com.google.android.play:core:1.10.3")
    implementation("com.google.android.play:core-ktx:1.8.1")

    implementation("com.google.android.flexbox:flexbox:3.0.0")

    implementation("com.google.accompanist:accompanist-flowlayout:0.24.13-rc")
}