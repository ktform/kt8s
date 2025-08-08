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

package dev.ktform.kt8s.container.packages.mlops

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.gitops.ArgoWorkflows

class KubeFlink(val version: String ) :
  Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String =
    `package`.render(version, env)

  override suspend fun versions(): List<String> = `package`.versions(Environment.default)
  override suspend fun render(): String = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/apache/flink-kubernetes-operator"

    val DEFAULT_VERSIONS = listOf(
      "2.5.0",
      "2.4.0",
      "2.3.0",
    )

    val `package` = Package(
      packageName = "flink-kubernetes-operator",
      repo = REPO,
      availableVersions = {
        val client = GithubClient()
        client.getTags(REPO)
          .getOrElse { DEFAULT_VERSIONS }
          .filter { !it.contains("-") && !it.contains("rc") }
          .map { it.removePrefix("v") }
          .distinct()
      },
      repoVersion = Package.withVPrefix
    )
  }
}