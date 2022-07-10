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
include(":nativetemplates")
include(":domain")
include(":data-database")
include(":shared-android")
include(":shared-base")
include(":data")
include(":data-network")
include(":data-datastore")
include(":feature-home")
include(":feature-quiz")
include(":feature-settings")
include(":feature-sync")
