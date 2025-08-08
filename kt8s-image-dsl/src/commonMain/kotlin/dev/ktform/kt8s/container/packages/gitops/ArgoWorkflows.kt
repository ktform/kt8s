package dev.ktform.kt8s.container.packages.gitops

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient

data class ArgoWorkflows(val version: String ) : Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String = `package`.render(version, env)

  override suspend fun versions(): List<String> = `package`.versions(Environment.default)
  override suspend fun render(): String = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/argoproj/argo-workflows"

    val DEFAULT_VERSIONS = listOf(
      "3.7.0",
      "3.6.10",
      "3.6.9",
      "3.6.8",
    )

    val `package` = Package(
        packageName = "argo-workflows",
        repo = REPO,
        availableVersions = {
            val client = GithubClient()
            client.getTags(REPO)
                .getOrElse { DEFAULT_VERSIONS }
                .filter { !it.contains("-") }
                .map { it.removePrefix("v") }
                .distinct()
        },
        repoVersion = Package.Companion.withVPrefix
    )
  }
}