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
import dev.ktform.kt8s.container.fetchers.GolangVersionFetcher
import dev.ktform.kt8s.container.versions.GolangVersion

class Golang(val versions: GolangVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, GolangVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/golang/go"
        const val RELEASE_PREFIX = "go"

        val DEFAULT_VERSIONS = listOf("1.24.6", "1.24.5")

        val `package` =
            Package(
                packageName = "go"
                //      repo = REPO,
                //
                //      repoVersion = { version, toRepo ->
                //        if (toRepo) {
                //          "$RELEASE_PREFIX$version"
                //        } else {
                //          version
                //        }
                //      },
                //      availableVersions = { _ ->
                //        val client = GithubClient()
                //        client.getTags(REPO)
                //          .map { all -> all.filter { v -> v.startsWith(RELEASE_PREFIX) }.map { v
                // -> v.substringAfter(RELEASE_PREFIX) }
                //            .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
                //            .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
                //            .sortedDescending()
                //            .map(Version::toString)
                //            .toList()
                //          }
                //      }
            )
    }
}
