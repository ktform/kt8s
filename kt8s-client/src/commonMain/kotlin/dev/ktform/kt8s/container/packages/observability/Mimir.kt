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
package dev.ktform.kt8s.container.packages.observability

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.MimirVersionFetcher
import dev.ktform.kt8s.container.versions.MimirVersion

class Mimir(val versions: MimirVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, MimirVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/grafana/mimir"
        const val DISTRIBUTED_PREFIX = "mimir-distributed-"

        val DEFAULT_VERSIONS = listOf("5.7.0", "5.6.1", "5.6.0")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "loki"
                //      repo = REPO,
                //
                //      repoVersion = { version, toRepo ->
                //        if (toRepo) {
                //          "$DISTRIBUTED_PREFIX$version"
                //        } else {
                //          version
                //        }
                //      },
                //      availableVersions = { _ ->
                //      val client = GithubClient()
                //      client.getTags(REPO)
                //        .map { all ->
                //          all
                //            .filter { it.startsWith(DISTRIBUTED_PREFIX) }
                //            .map { it.removePrefix(DISTRIBUTED_PREFIX).trim() }
                //            .mapNotNull { s -> runCatching { s.toVersion() }.getOrNull() }
                //            .filter { it.isStable && !it.isPreRelease }
                //            .sortedDescending()
                //            .map { it.toString() }
                //            .toList()
                //        }
                //      },
            )
    }
}
