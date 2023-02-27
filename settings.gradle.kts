pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "cpa-word-problem"
include(":app")
include(":core:domain")
include(":core:common")
include(":core:android")
include(":core:navigation")
include(":core:data")
include(":core:data-database")
include(":core:data-datastore")
include(":core:data-network")
include(":feature:home")
include(":feature:quiz")
include(":feature:settings")
include(":sync")
include(":benchmark")
