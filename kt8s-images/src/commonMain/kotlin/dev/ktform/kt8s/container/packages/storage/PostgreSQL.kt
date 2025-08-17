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
import dev.ktform.kt8s.container.fetchers.PostgreSQLVersionFetcher
import dev.ktform.kt8s.container.versions.PostgreSQLVersion

class PostgreSQL(val versions: PostgreSQLVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, PostgreSQLVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/postgres/postgres"
        const val RELEASE_PREFIX = "REL_"
        val DEFAULT_VERSIONS = listOf("17.5", "17.4")

        val `package` =
            Package(
                packageName = "postgresql"
                // repo = REPO,
                // repoVersion = { version, toRepo ->
                //   if (toRepo) {
                //     "$RELEASE_PREFIX${version.replace(".", "_")}"
                //   } else {
                //     version
                //   }
                // },
                // availableVersions = { _ ->
                //   val client = GithubClient()
                //   client.getTags(REPO).map { all ->
                //     all.filter { v -> v.startsWith(RELEASE_PREFIX) }
                //       .map { v -> v.substringAfter(RELEASE_PREFIX).replace("_", ".") }
                //       .filter { v -> !v.lowercase().contains("beta") &&
                // !v.lowercase().contains("rc") }
                //       .sortedDescending()
                //   }
                // }
            )
    }
}
