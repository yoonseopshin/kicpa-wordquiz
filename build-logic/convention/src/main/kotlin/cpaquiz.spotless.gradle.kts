plugins {
    id("com.diffplug.spotless")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(libs.findVersion("ktlint").get().toString())
            .editorConfigOverride(
                mapOf("android" to "true"),
            )
            .customRuleSets(
                listOf("io.nlopez.compose.rules:ktlint:0.3.7")
            )
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
    }
}