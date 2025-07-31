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

import arrow.core.getOrElse
import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient

class VPA(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> =
    `package`.versions(env)

  override suspend fun render(version: String, env: Environment): Either<String, String> =
    `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

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
      availableVersions = { _ ->
        val client = GithubClient()
        client.getTags(REPO)
//          .getOrElse { fallback }
//          .mapNotNull { tag ->
//            if (tag.startsWith("vertical-pod-autoscaler-")) tag.removePrefix("vertical-pod-autoscaler-") else null
//          }
//          .filter { !it.contains("-") && !it.contains("rc") }
//          .distinct()
      },
      repoVersion = { v, toRepo -> if (toRepo) "vertical-pod-autoscaler-$v" else v },
    )
  }
}
