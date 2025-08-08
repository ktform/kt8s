package dev.ktform.kt8s.container.packages.observability

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.gitops.ArgoWorkflows

class Loki(val version: String ) :
  Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String =
    `package`.render(version, env)

  override suspend fun versions(): List<String> = ArgoWorkflows.Companion.`package`.versions(Environment.default)
  override suspend fun render(): String = ArgoWorkflows.Companion.`package`.render(version, Environment.default)

  companion object {
    const val REPO = ""

    val DEFAULT_VERSIONS = listOf(
      "",
    )

    val `package` = Package(
      packageName = "uv",
      repo = "",
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