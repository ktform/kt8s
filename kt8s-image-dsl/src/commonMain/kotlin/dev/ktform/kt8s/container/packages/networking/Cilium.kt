package dev.ktform.kt8s.container.packages.networking

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class Cilium(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    val DEFAULT_VERSIONS = listOf(
      "1.18.0",
      "1.17.6",
    )

    val `package` = Package(
      packageName = "cilium",
      repo = "https://github.com/cilium/cilium",

      repoVersion = Package.withVPrefix,
    )
  }
}