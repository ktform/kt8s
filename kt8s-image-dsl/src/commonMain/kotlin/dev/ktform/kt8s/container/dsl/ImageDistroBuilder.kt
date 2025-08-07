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

import dev.ktform.kt8s.container.Distro

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