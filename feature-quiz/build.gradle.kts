plugins {
    id("dagger.hilt.android.plugin")
    id("cpaquiz.android.feature")
    id("cpaquiz.spotless")
}

dependencies {
    implementation(project(":nativetemplates"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    implementation("com.google.android.gms:play-services-ads:20.6.0")

    implementation("com.google.android.play:core:1.10.3")
    implementation("com.google.android.play:core-ktx:1.8.1")

    implementation("androidx.recyclerview:recyclerview:1.3.0-alpha02")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}