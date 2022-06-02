plugins {
    id("com.diffplug.spotless")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(libs.findVersion("ktlint").get().toString()).userData(
            mapOf(
                "android" to "true",
                "disabled_rules" to "no-wildcard-imports",
                "max_line_length" to "120",
            )
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