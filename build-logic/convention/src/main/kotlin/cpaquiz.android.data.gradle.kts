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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared-android"))
    implementation(project(":shared-base"))
    implementation(project(":domain"))

    add("implementation", libs.findLibrary("hilt.android").get())
    add("kapt", libs.findLibrary("hilt.compiler").get())

    add("implementation", libs.findLibrary("coroutines-android").get())

    add("implementation", libs.findLibrary("retrofit-kotlinx-serialization").get())
    add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findBundle("androidx-test").get())
}
