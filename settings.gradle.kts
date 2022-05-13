rootProject.name = "cpa-word-problem"
include(":app")
include(":nativetemplates")
include(":domain")
include(":shared_android")
include(":shared_base")

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}