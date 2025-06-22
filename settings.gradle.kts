pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rate Gap"
include(":app")
include(":core")
include(":feature:network")
include(":feature:storage")
include(":feature:screen:rate")
include(":feature:domain:currency")
include(":feature:screen:splash")
include(":feature:domain:user-preference")
include(":feature:screen:pick-currency")
