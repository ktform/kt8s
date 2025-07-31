package dev.ktform.kt8s.container.packages.observability

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class Prometheus(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    val DEFAULT_VERSIONS = listOf(
      "3.5.0",
      "3.4.2",
      "3.4.1",
      "3.4.0",
    )

    val `package` = Package(
      packageName = "prometheus",
      repo = "https://github.com/prometheus/prometheus",

      repoVersion = Package.withVPrefix,
    )
  }
}