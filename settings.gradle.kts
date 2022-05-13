rootProject.name = "cpa-word-problem"

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include(":app")
include(":nativetemplates")
include(":domain")
include(":data-database")
include(":shared-android")
include(":shared-base")
include(":data")
include(":data-network")
include(":data-datastore")
