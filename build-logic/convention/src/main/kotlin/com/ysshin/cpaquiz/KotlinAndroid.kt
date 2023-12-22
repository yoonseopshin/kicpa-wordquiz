package com.ysshin.cpaquiz

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()

            allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

            freeCompilerArgs += listOf(
                // Enable compose metrics
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${rootProject.file(".").absolutePath}/compose/metric",

                // Enable compose reports
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${rootProject.file(".").absolutePath}/compose/report",
                
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
                // Enable experimental kotlinx serialization APIs
                "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
                // Enable experimental material3 APIs
                "-opt-in=kotlinx.serialization.ExperimentalMaterial3Api",
                // Enable experimental lifecycle compose APIs
                "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",

                // https://github.com/Kotlin/kotlinx.serialization/issues/2145#issuecomment-1653091753
                "-Xstring-concat=inline",
            )
        }

        buildFeatures {
            buildConfig = true
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler").get().toString()
        }
    }
}

private fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
