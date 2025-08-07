package dev.ktform.kt8s.container.dsl

import dev.ktform.kt8s.container.Package


@ImageDsl
class ImageDistroDependenciesBuilder {
  data class DistroDependencies(
    val build: List<String> = emptyList(),
    val run: List<String> = emptyList(),
    val buildAndRun: List<String> = emptyList(),

    val packageBuildDependencies: List<Package> = emptyList(),
    val packageRunDependencies: List<Package> = emptyList(),
  ) {
    fun getBuildDependencies(): Set<String> = (build + buildAndRun).distinct().toSet()
    fun getRunDependencies(): Set<String> = (run + buildAndRun).distinct().toSet()

    fun getPackageBuildDependencies(): Set<Package> = packageBuildDependencies.toSet()
    fun getPackageRunDependencies(): Set<Package> = packageRunDependencies.toSet()
  }

  private val buildDeps = mutableListOf<String>()
  private val runDeps = mutableListOf<String>()
  private val buildAndRunDeps = mutableListOf<String>()
  private val packageBuildDependencies = mutableListOf<Package>()
  private val packageRunDependencies = mutableListOf<Package>()

  fun buildAndRun(vararg packages: String) {
    buildAndRunDeps.addAll(packages)
  }

  fun build(vararg packages: String) {
    buildDeps.addAll(packages)
  }

  fun run(vararg packages: String) {
    runDeps.addAll(packages)
  }

  fun buildPackages(vararg packages: Package) {
    packageBuildDependencies.addAll(packages)
  }

  fun runPackages(vararg packages: Package) {
    packageRunDependencies.addAll(packages)
  }

  internal fun build(): DistroDependencies = DistroDependencies(
    build = buildDeps.toList(),
    run = runDeps.toList(),
    buildAndRun = buildAndRunDeps.toList(),
    packageRunDependencies = packageRunDependencies,
    packageBuildDependencies = packageBuildDependencies,
  )
}