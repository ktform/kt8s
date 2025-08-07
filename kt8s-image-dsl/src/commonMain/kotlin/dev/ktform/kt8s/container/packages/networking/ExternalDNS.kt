package dev.ktform.kt8s.container.packages.networking

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class ExternalDNS (val version: String = dev.ktform.kt8s.container.packages.networking.ExternalDNS.Companion.`package`.latestVersion(Environment.default)) : Renderable {
  override fun versions(env: Environment): List<String> = dev.ktform.kt8s.container.packages.networking.ExternalDNS.Companion.`package`.versions(env)
  override fun render(version: String, env: Environment): String = dev.ktform.kt8s.container.packages.networking.ExternalDNS.Companion.`package`.render(version, env)

  companion object {
    val `package` = Package(
      packageName = "externaldns",
    )
  }
}
