package dev.ktform.kt8s.container.packages.development

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient

class Cosign(val version: String ) : Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String = `package`.render(version, env)

  override suspend fun versions(): List<String> = `package`.versions(Environment.default)
  override suspend fun render(): String = `package`.render(version, Environment.default)

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
        availableVersions = {
            val client = GithubClient()
            client.getTags(REPO)
                .getOrElse { DEFAULT_VERSIONS }
                .filter { !it.contains("-") && !it.contains("rc") }
                .map { it.removePrefix("v") }
                .distinct()
        },
        repoVersion = Package.withVPrefix
    )
  }
}