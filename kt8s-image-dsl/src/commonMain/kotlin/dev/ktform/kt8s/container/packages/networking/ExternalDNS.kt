package dev.ktform.kt8s.container.packages.networking

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class ExternalDNS(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    val DEFAULT_VERSIONS = listOf(
      "0.18.0",
      "0.17.0",
      "0.16.1",
    )

    val `package` = Package(
      packageName = "external-dns",
      repo = "https://github.com/kubernetes-sigs/external-dns",
      repoVersion = Package.withVPrefix,
    )
  }
}