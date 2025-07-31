/*
 * Copyright (C) 2016-2025 Yuriy Yarosh
 * All rights reserved.
 *
 * SPDX-License-Identifier: MPL-2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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