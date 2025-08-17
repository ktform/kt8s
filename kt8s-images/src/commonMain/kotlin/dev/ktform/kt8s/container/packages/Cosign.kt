package dev.ktform.kt8s.container.packages.development

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.CosignVersionFetcher

class Cosign(val versions: Versions.CosignVersion) : Renderable {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, CosignVersionFetcher, env)

  companion object {
    const val REPO = "https://github.com/sigstore/cosign"

    val DEFAULT_VERSIONS = listOf(
      "2.5.3",
      "2.5.2",
    )

    val `package` = Package(
      packageName = "cosign",
      // repo = REPO,
      // repoVersion = Package.withVPrefix,
    )
  }
}