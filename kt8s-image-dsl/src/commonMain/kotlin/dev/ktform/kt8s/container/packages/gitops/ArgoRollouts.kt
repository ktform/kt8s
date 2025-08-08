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

package dev.ktform.kt8s.container.packages

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient

data class ArgoRollouts(val version: String) : Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String = `package`.render(version, env)

  override suspend fun versions(): List<String> = `package`.versions(Environment.default)
  override suspend fun render(): String = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/argoproj/argo-rollouts"

    val DEFAULT_VERSIONS = listOf(
      "1.8.3",
      "1.8.2",
      "1.8.1",
      "1.8.0",
      "1.7.2",
      "1.7.1",
      "1.7.0",
    )

    val `package` = Package(
      packageName = "argo-rollouts",
      repo = REPO,
      availableVersions = {
        val client = GithubClient()
        client.getTags(REPO)
          .getOrElse { DEFAULT_VERSIONS }
          .filter { !it.contains("-") }
          .map { it.removePrefix("v") }
          .distinct()
      },
      repoVersion = Package.withVPrefix
    )
  }
}
