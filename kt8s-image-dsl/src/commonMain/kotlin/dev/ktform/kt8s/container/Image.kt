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

package dev.ktform.kt8s.container

import arrow.core.*
import dev.ktform.kt8s.container.dsl.*

@ImageDsl
class Image {
  private var packageName: Option<String> = none()
  private var repo: Option<String> = none()

  private val distros = mutableMapOf<Distro, ImageDistroBuilder.DistroConfig>()
  private var endpoints = ImageEndpointsBuilder.Endpoints()
  private var probes = ImageProbesBuilder.Probes()
  private var signals = ImageSignalConfigBuilder.Signals()

  private var runtime: Boolean = false
  private var providers: NonEmptyList<Environment.Provider> = Environment.Provider.all

  private var flavours = mutableListOf<Package>()

  private var repoVersion: Option<(String, toRepo: Boolean) -> String> = none()
  private val availableVersions: Option<(Environment) -> List<String>> = none()

  fun name(name: String) {
    packageName = name.some()
  }

  fun repo(repo: String) {
    this.repo = repo.some()
  }

  fun runtime() {
    runtime = true
  }

  fun local() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.Local)
    } else {
      providers += Environment.Provider.Local
    }
  }

  fun aws() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.AWS)
    } else {
      providers += Environment.Provider.AWS
    }
  }

  fun gcp() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.GCP)
    } else {
      providers += Environment.Provider.GCP
    }
  }

  fun digitalOcean() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.DigitalOcean)
    } else {
      providers += Environment.Provider.DigitalOcean
    }
  }

  fun azure() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.Azure)
    } else {
      providers += Environment.Provider.Azure
    }
  }

  fun vultr() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.Vultr)
    } else {
      providers += Environment.Provider.Vultr
    }
  }

  fun hetzner() {
    if (providers.all == providers) {
      providers = nonEmptyListOf(Environment.Provider.Hetzner)
    } else {
      providers += Environment.Provider.Hetzner
    }
  }

  fun alpine(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.Alpine)
    builder.block()
    distros[Distro.Alpine] = builder.build()
  }

  fun debian(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.Debian)
    builder.block()
    distros[Distro.Debian] = builder.build()
  }

  fun photon(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.Photon)
    builder.block()
    distros[Distro.Photon] = builder.build()
  }

  fun oracle(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.Oracle)
    builder.block()
    distros[Distro.Oracle] = builder.build()
  }

  fun rocky(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.Rocky)
    builder.block()
    distros[Distro.Rocky] = builder.build()
  }

  fun ubi(block: ImageDistroBuilder.() -> Unit) {
    val builder = ImageDistroBuilder(Distro.UBI)
    builder.block()
    distros[Distro.UBI] = builder.build()
  }

  fun flavours(vararg flavours: Package) {
    if (flavours.isEmpty()) {
      this.flavours.addAll(flavours)
    } else {
      throw IllegalArgumentException("Flavours already set")
    }
  }

  fun endpoints(block: ImageEndpointsBuilder.() -> Unit) {
    val builder = ImageEndpointsBuilder()
    builder.block()
    endpoints = builder.build()
  }

  fun probes(block: ImageProbesBuilder.() -> Unit) {
    val builder = ImageProbesBuilder()
    builder.block()
    probes = builder.build()
  }

  fun signals(block: ImageSignalConfigBuilder.() -> Unit) {
    val builder = ImageSignalConfigBuilder()
    builder.block()
    signals = builder.build()
  }

  fun toPackage(): Package {
    val allDependencies = mutableMapOf<Distro, NonEmptyList<Package>>()
    val buildCommands = mutableMapOf<Distro, NonEmptyList<String>>()

    distros.forEach { (distro, config) ->
      val deps = config.dependencies.getAllDependencies().mapNotNull { depName ->
        Package.all[depName]
      }

      if (deps.isNotEmpty()) {
        allDependencies[distro] = deps.toNonEmptyListOrThrow()
      }

      val commands = mutableListOf<String>()

      if (commands.isNotEmpty()) {
        buildCommands[distro] = commands.toNonEmptyListOrThrow()
      }
    }

    return Package(
      packageName = packageName.getOrElse { throw Exception("Unable to determine package name") },
//      dependencies = allDependencies,
//      build = buildCommands,
    )
  }
}
