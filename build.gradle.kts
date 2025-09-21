import nl.littlerobots.vcu.plugin.resolver.VersionSelectors
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
  alias(libs.plugins.version.update)
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.maven.publish) apply false
}

versionCatalogUpdate {
  versionSelector(VersionSelectors.PREFER_STABLE)
}

// Function to read and parse the .env file
fun loadDotEnv(): Map<String, String> {
  val envMap = mutableMapOf<String, String>()
  val envFile = File("${project.rootDir}/.env")

  if (envFile.exists()) {
    envFile.forEachLine { line ->
      val trimmedLine = line.trim()
      if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("#")) {
        val parts = trimmedLine.split("=", limit = 2)
        if (parts.size == 2) {
          val key = parts[0].trim()
          val value = parts[1].trim().trim('"').trim()
          if (key.isNotEmpty()) {
            envMap[key] = value
          }
        }
      }
    }
  } else {
    println("Warning: .env file not found at ${project.rootDir}/.env")
  }

  return envMap
}

val envVars = loadDotEnv()

allprojects {
  val variables = listOf("GITHUB_TOKEN")

  tasks.withType<Test> {
    variables.forEach { variable -> environment(variable, envVars[variable] ?: "")}

    testLogging {
      events("passed", "skipped", "failed")
      showStandardStreams = true
    }
  }

  // For JVM tests
  tasks.withType<JavaExec> {
    variables.forEach { variable -> environment(variable, envVars[variable] ?: "")}
  }

  tasks.matching { it.name.toDefaultLowerCase().contains("test") }.configureEach {
    if (this is Exec) {
      variables.forEach { variable -> environment(variable, envVars[variable] ?: "")}
    }
  }
}
