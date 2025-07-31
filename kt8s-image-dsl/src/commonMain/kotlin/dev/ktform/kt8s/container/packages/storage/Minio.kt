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

package dev.ktform.kt8s.container.packages.storage

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.mlops.KubeFlink.Companion.RELEASE_PREFIX
import dev.ktform.kt8s.container.packages.mlops.KubeFlink.Companion.REPO

class Minio(val version: String) :
  Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> =
    `package`.versions(env)

  override suspend fun render(version: String, env: Environment): Either<String, String> =
    `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    const val RELEASE_PREFIX = "RELEASE."
    const val REPO = "minio/minio"

    val DEFAULT_VERSIONS = listOf(
      "2025-07-23T15-54-02Z",
      "2025-07-18T21-56-31Z"
    )

    val `package` = Package(
      packageName = "minio",
      repo = "https://github.com/minio/minio",
      repoVersion = { version, toRepo ->
        if (toRepo) {
          "$RELEASE_PREFIX$version"
        } else {
          version
        }
      },
      availableVersions = { _ ->
        val client = GithubClient()
        client.getTags(REPO).map { it.filter { v -> v.startsWith(RELEASE_PREFIX) }.map { v -> v.substringAfter(RELEASE_PREFIX) }}
      }
    )
  }
}
