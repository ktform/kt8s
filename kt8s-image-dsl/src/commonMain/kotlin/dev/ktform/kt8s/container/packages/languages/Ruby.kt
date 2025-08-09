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

package dev.ktform.kt8s.container.packages.languages

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient
import io.github.z4kn4fein.semver.toVersion

class Ruby(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> = `package`.versions(env)
  override suspend fun render(version: String, env: Environment): Either<String, String> = `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    const val REPO = "https://github.com/ruby/ruby"

    val DEFAULT_VERSIONS = listOf(
      "3.4.5",
      "3.4.4",
      "3.4.3",
    )

    val `package` = Package(
      packageName = "ruby",
      repo = REPO,
      repoVersion = { version, toRepo ->
        if (toRepo) {
          "v${version.replace(".", "_")}"
        } else {
          version
        }
      },
      availableVersions = { _ ->
        val client = GithubClient()
        client.getTags(REPO).map { all ->
          all.map { it.removePrefix("v").replace("_", ".").trim() }
            .mapNotNull { s -> runCatching { s.toVersion() }.getOrNull() }
            .filter { it.isStable }
            .sortedDescending()
            .map { it.toString() }
            .toList()
        }
      },
    )
  }
}