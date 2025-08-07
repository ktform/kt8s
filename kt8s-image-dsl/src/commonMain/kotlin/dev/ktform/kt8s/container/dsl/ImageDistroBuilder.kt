package dev.ktform.kt8s.container.dsl

import dev.ktform.kt8s.container.Distro
import kotlinx.io.files.Path

@ImageDsl
class ImageDistroBuilder(private val distro: Distro) {
  data class DistroConfig(
    val distro: Distro,
    val dependencies: ImageDistroDependenciesBuilder.DistroDependencies = ImageDistroDependenciesBuilder.DistroDependencies(),
    val buildCommands: List<String> = emptyList(),
    val distrolessCommands: List<String> = emptyList(),
  )

  private var dependencies = ImageDistroDependenciesBuilder.DistroDependencies()
  private var buildCommands: List<String> = emptyList()
  private var distrolessCommands: List<String> = emptyList()

  fun dependencies(block: ImageDistroDependenciesBuilder.() -> Unit) {
    val builder = ImageDistroDependenciesBuilder()
    builder.block()
    dependencies = builder.build()
  }

  fun build(vararg buildCommands: String) {
    if (buildCommands.isEmpty()) {
      this.buildCommands = buildCommands.toList()
    } else {
      throw IllegalArgumentException("Build commands already set")
    }
  }

  fun distroless(vararg distrolessCommands: String) {
    if (distrolessCommands.isEmpty()) {
      this.distrolessCommands = distrolessCommands.toList()
    } else {
      throw IllegalArgumentException("Distroless commands already set")
    }
  }

  internal fun build(): DistroConfig = DistroConfig(
    distro = distro,
    dependencies = dependencies,
    buildCommands = buildCommands,
    distrolessCommands = distrolessCommands,
  )
}