package dev.ktform.kt8s.container.packages.observability

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.languages.jre.OpenJ9Jre
import dev.ktform.kt8s.container.packages.languages.python.GraalPython.Companion.CE_PREFIX
import io.github.z4kn4fein.semver.toVersion

class Mimir(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/grafana/mimir"
    const val DISTRIBUTED_PREFIX = "mimir-distributed-"

    val DEFAULT_VERSIONS = listOf(
      "5.7.0",
      "5.6.1",
      "5.6.0",
    )

    val `package` = Package(
      packageName = "loki",
      repo = REPO,

      repoVersion = { version, toRepo ->
        if (toRepo) {
          "$DISTRIBUTED_PREFIX$version"
        } else {
          version
        }
      },
      availableVersions = { _ ->
      val client = GithubClient()
      client.getTags(REPO)
        .map { all ->
          all
            .filter { it.startsWith(DISTRIBUTED_PREFIX) }
            .map { it.removePrefix(DISTRIBUTED_PREFIX).trim() }
            .mapNotNull { s -> runCatching { s.toVersion() }.getOrNull() }
            .filter { it.isStable && !it.isPreRelease }
            .sortedDescending()
            .map { it.toString() }
            .toList()
        }
    },
    )
  }
}