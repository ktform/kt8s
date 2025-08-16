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
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.MinioVersionFetcher

class Minio(val versions: Versions.MinioVersion) : Renderable  {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, MinioVersionFetcher, env)

  companion object {
    const val RELEASE_PREFIX = "RELEASE."
    const val REPO = "minio/minio"

    val DEFAULT_VERSIONS = listOf(
      "2025-07-23T15-54-02Z",
      "2025-07-18T21-56-31Z"
    )

    val latest = DEFAULT_VERSIONS.first()

    val `package` = Package(
      packageName = "minio",
//      repo = "https://github.com/minio/minio",
//      repoVersion = { version, toRepo ->
//        if (toRepo) {
//          "$RELEASE_PREFIX$version"
//        } else {
//          version
//        }
//      },
//      availableVersions = { _ ->
//        val client = GithubClient()
//        client.getTags(REPO).map { it.filter { v -> v.startsWith(RELEASE_PREFIX) }.map { v -> v.substringAfter(RELEASE_PREFIX) }}
//      }
    )
  }
}
