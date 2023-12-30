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

rootProject.name = "kicpa-wordquiz"
include(":app")
include(":core:domain")
include(":core:common")
include(":core:android")
include(":core:navigation")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:designsystem")
include(":feature:home")
include(":feature:quiz")
include(":feature:settings")
include(":sync")
include(":baselineprofile")
