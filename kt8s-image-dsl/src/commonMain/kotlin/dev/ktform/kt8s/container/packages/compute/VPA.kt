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

package dev.ktform.kt8s.container.packages.compute

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import arrow.core.getOrElse
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.Terraform

class VPA(val version: String ) :
  Renderable {
  override suspend fun versions(env: Environment): List<String> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): String =
    `package`.render(version, env)

  override suspend fun versions(): List<String> = Terraform.Companion.`package`.versions(Environment.default)
  override suspend fun render(): String = Terraform.Companion.`package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/kubernetes/autoscaler"

    val DEFAULT_VERSIONS = listOf(
      "1.0.0",
      "0.14.0",
      "0.13.0",
    )

    val `package` = Package(
      packageName = "vpa",
      repo = REPO,
      availableVersions = {
        val client = GithubClient()
        client.getTags(REPO)
          .getOrElse { DEFAULT_VERSIONS.map { "vertical-pod-autoscaler-$it" } }
          .mapNotNull { tag ->
            if (tag.startsWith("vertical-pod-autoscaler-")) tag.removePrefix("vertical-pod-autoscaler-") else null
          }
          .filter { !it.contains("-") && !it.contains("rc") }
          .distinct()
      },
      repoVersion = { v, toRepo -> if (toRepo) "vertical-pod-autoscaler-$v" else v }
    )
  }
}
