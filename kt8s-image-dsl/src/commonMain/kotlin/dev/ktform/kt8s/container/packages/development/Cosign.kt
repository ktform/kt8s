package dev.ktform.kt8s.container.packages.development

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class Cosign(val version: String) : Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/sigstore/cosign"

    val DEFAULT_VERSIONS = listOf(
      "2.4.1",
      "2.4.0",
      "2.3.0",
    )

    val `package` = Package(
      packageName = "cosign",
      repo = REPO,
      repoVersion = Package.withVPrefix,
    )
  }
}