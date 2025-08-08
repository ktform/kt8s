package dev.ktform.kt8s.container.packages.gitops

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

data class ArgoWorkflows(val version: String) : Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

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
      repoVersion = Package.Companion.withVPrefix,
    )
  }
}