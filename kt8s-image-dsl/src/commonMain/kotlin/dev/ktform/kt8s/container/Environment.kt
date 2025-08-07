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
import dev.ktform.kt8s.container.packages.languages.jdk.GraalVMJdk
import dev.ktform.kt8s.container.packages.languages.jdk.OpenJ9Jdk
import dev.ktform.kt8s.container.packages.languages.jdk.TemurinJdk
import dev.ktform.kt8s.container.packages.languages.jre.GraalVMJre
import dev.ktform.kt8s.container.packages.languages.jre.OpenJ9Jre
import dev.ktform.kt8s.container.packages.languages.jre.TemurinJre
import dev.ktform.kt8s.container.packages.languages.python.CPython
import dev.ktform.kt8s.container.packages.languages.python.GraalPython
import dev.ktform.kt8s.container.packages.languages.python.PyPy
import dev.ktform.kt8s.container.packages.languages.ruby.*
import dev.ktform.kt8s.container.packages.languages.rust.NightlyRust
import dev.ktform.kt8s.container.packages.languages.rust.StableRust
import io.ktor.util.*

data class Environment(
  val name: String = "default",
  val provider: Provider,
  val distro: Distro,
  val flavours: List<Package>,
) {
  companion object {
    val all: List<Environment> =
      Distro.all.flatMap { (_, distro) ->
        Provider.entries.map { Environment(it.name, it, distro, listOf()) }
      }

    internal val default = Environment(
      name = "default",
      provider = Provider.Local,
      distro = Distro.Debian,
      listOf(),
    )

    val defaultFlavours: NonEmptyList<Package> = nonEmptyListOf(
      OpenJ9Jdk.`package`,
      OpenJ9Jre.`package`,
      CPython.`package`,
      CRuby.`package`,
      StableRust.`package`,
    )

    val availableFlavours: NonEmptyList<Package> = nonEmptyListOf(
      GraalVMJdk.`package`,
      OpenJ9Jdk.`package`,
      TemurinJdk.`package`,
      GraalVMJre.`package`,
      OpenJ9Jre.`package`,
      TemurinJre.`package`,
      CPython.`package`,
      GraalPython.`package`,
      PyPy.`package`,
      CRuby.`package`,
      GraalTruffleRuby.`package`,
      JRuby.`package`,
      MRuby.`package`,
      Rbx.`package`,
      Ree.`package`,
      NightlyRust.`package`,
      StableRust.`package`,
    )
  }

  enum class Provider {
    Local,
    AWS,
    GCP,
    DigitalOcean,
    Azure,
    Vultr,
    Hetzner;

    companion object {
      val all: NonEmptyList<Provider> = Provider.entries.toNonEmptyListOrThrow()

      fun fromString(str: String): Option<Provider> = when (str.toLowerCasePreservingASCIIRules()) {
        "local" -> Local.some()
        "aws" -> AWS.some()
        "gcp" -> GCP.some()
        "do" -> DigitalOcean.some()
        "digitalocean" -> DigitalOcean.some()
        "azure" -> Azure.some()
        "vultr" -> Vultr.some()
        "hetzner" -> Hetzner.some()
        else -> none()
      }
    }
  }
}