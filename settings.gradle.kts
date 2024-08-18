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
  buildscript {
    repositories {
      mavenCentral()
      maven {
        url = uri("https://storage.googleapis.com/r8-releases/raw")
      }
    }
    dependencies {
      classpath("com.android.tools:r8:8.5.35")
    }
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "PlayInfo"
include(":app")
include(":benchmark")
