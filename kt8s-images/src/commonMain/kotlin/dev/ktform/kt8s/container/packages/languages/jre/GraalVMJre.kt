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

package dev.ktform.kt8s.container.packages.languages.jre

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.JavaVersionFetcher

class GraalVMJre(val versions: Versions.JavaVersion) : Renderable {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, JavaVersionFetcher, env)


  companion object {
    const val REPO = "https://github.com/oracle/graal"

    const val CE_PREFIX = "vm-ce-"

    val DEFAULT_VERSIONS = listOf(
      "",
    )

    val `package` = Package(
      packageName = "graalvmjre",
      // repo = REPO,

      // repoVersion = { version, toRepo ->
      //   if (toRepo) {
      //     "$CE_PREFIX$version"
      //   } else {
      //     version
      //   }
      // },
      // availableVersions = { _ ->
      //   val client = GithubClient()
      //   client.getTags(REPO).map { it.filter { v -> v.startsWith(CE_PREFIX) }.map { v -> v.substringAfter(CE_PREFIX) }}
      // }
    )
  }
}