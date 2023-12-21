import com.ysshin.cpaquiz.projectArchivesName
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.gms.oss-licenses-plugin")
    id("cpaquiz.spotless")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    namespace = "com.ysshin.cpaquiz.app"

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        archivesName = projectArchivesName
    }

    signingConfigs {
        create("release") {
            val secrets = Properties().apply {
                load(File("secrets.properties").inputStream())
            }

            storeFile = file(secrets["APP_KEYSTORE_FILE"].toString())
            storePassword = secrets["APP_KEYSTORE_PASSWORD"].toString()
            keyAlias = secrets["APP_KEY_ALIAS"].toString()
            keyPassword = secrets["APP_KEY_PASSWORD"].toString()
        }
    }

    buildTypes {
        val debug by getting {
            buildConfigField("boolean", "ENABLE_CRASHLYTICS", "false")
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
        val benchmark by creating {
            initWith(release)
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles("benchmark-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        // https://youtrack.jetbrains.com/issue/KT-46940
        // https://dagger.dev/hilt/gradle-setup#aggregating-task
        enableAggregatingTask = true
    }

    lint {
        disable.add("Instantiatable")
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:android"))
    implementation(project(":core:navigation"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:home"))
    implementation(project(":feature:quiz"))
    implementation(project(":feature:settings"))
    implementation(project(":sync"))

    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsizeclass)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)

    debugImplementation(libs.leakcanary)
}
