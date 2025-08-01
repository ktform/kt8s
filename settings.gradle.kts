import org.gradle.internal.impldep.org.bouncycastle.oer.its.etsi102941.Url
import org.gradle.kotlin.dsl.maven

pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    maven {
      url = uri("https://packages.confluent.io/maven/")
    }
  }
}

rootProject.name = "kt8s"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":kt8s-client")
include(":kt8s-compiler")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_21)) {
  """
    Sumicare requires JDK 21 but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
  """.trimIndent()
}
