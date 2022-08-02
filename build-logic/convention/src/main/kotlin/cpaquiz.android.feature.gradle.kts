import com.ysshin.cpaquiz.configureKotlinAndroid

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    configureKotlinAndroid(this)

    defaultConfig {
        targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.findVersion("compose").get().toString()
    }

}

dependencies {
    implementation(project(":shared-android"))
    implementation(project(":shared-base"))
    implementation(project(":shared-navigation"))
    implementation(project(":domain"))

    add("implementation", libs.findLibrary("hilt.android").get())
    add("kapt", libs.findLibrary("hilt.compiler").get())

    add("implementation", libs.findLibrary("coroutines-android").get())

    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findBundle("androidx-test").get())

    add("implementation", libs.findLibrary("compose.material3").get())
}
