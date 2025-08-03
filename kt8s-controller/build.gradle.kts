plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktor)
  alias(libs.plugins.maven.publish)
  kotlin("jvm")
  application
}

spotless {
  kotlin {
    ktfmt("0.56").kotlinlangStyle()
  }
}

buildscript {
  dependencies {
    classpath(kotlin("gradle-plugin", version = "2.2.0"))
  }
}

application {
  mainClass.set("dev.ktform.kt8s.controller.MainKt")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}

dependencies {
  implementation(libs.bundles.common)
  implementation(libs.bundles.arrow)

  testImplementation(libs.bundles.common.testing)
  testImplementation(libs.bundles.jvm.testing)
}