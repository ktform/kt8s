import dev.ktform.kt8s.crds.Crds.Companion.writeAllCrds
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.maven.publish)
  kotlin("jvm")
}

spotless {
  kotlin {
    licenseHeaderFile(rootProject.file("header.kt"))
    target("**/*.kt")
    targetExclude("**/build/**", "**/.gradle/**", "**/tmp/**", "**/resources/**")
    ktfmt("0.56").kotlinlangStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }
}

buildscript {
  dependencies {
    classpath(kotlin("gradle-plugin", version = "2.2.0"))
  }
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}

dependencies {
  implementation(libs.bundles.compiler)
  implementation(libs.bundles.common)
  implementation(libs.bundles.arrow)

//  testImplementation(libs.bundles.compiler.testing)
  testImplementation(libs.bundles.common.testing)
  testImplementation(libs.bundles.jvm.testing)

  ksp(libs.arrow.optics.compiler)
}

// Create a dedicated task for CRD generation
val generateCrds by tasks.registering {
  description = "Generate CRD files from external sources"
  group = "build"
  
  val outputDir = project.rootDir.resolve("kt8s-compiler/src/main/resources/crds")
  outputs.dir(outputDir)
  
  doLast {
    writeAllCrds(outputDir)
  }
}

// Make compileKotlin depend on CRD generation
tasks.named("compileKotlin") {
  dependsOn(generateCrds)
}

tasks.named("processResources") {
    dependsOn(generateCrds)
}