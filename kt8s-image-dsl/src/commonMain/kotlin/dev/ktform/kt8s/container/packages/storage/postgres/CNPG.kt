package dev.ktform.kt8s.container.packages.storage.postgres

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class CNPG(val version: String = `package`.latestVersion(Environment.default)) :
  Renderable {
  override fun versions(env: Environment): List<String> = `package`.versions(env)
  override fun render(version: String, env: Environment): String =
    `package`.render(version, env)

  companion object {
    val `package` = Package(
      packageName = "cnpg",
      repo = "",
    )
  }
}