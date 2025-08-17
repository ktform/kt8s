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

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.KubeFlinkVersionFetcher
import dev.ktform.kt8s.container.versions.KubeFlinkVersion

class KubeFlink(val versions: KubeFlinkVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, KubeFlinkVersionFetcher, env)

    companion object {
        const val RELEASE_PREFIX = "release-"

        const val REPO = "https://github.com/apache/flink-kubernetes-operator"

        val DEFAULT_VERSIONS = listOf("1.12.1", "1.12.0", "1.11.0")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "flink-kubernetes-operator"
                // repo = REPO,
                // repoVersion = { version, toRepo ->
                //   if (toRepo) {
                //     "$RELEASE_PREFIX$version"
                //   } else {
                //     version
                //   }
                // },
                // availableVersions = { _ ->
                //   val client = GithubClient()
                //   client.getTags(REPO).map { it.filter { v -> v.startsWith(RELEASE_PREFIX) }.map
                // { v -> v.substringAfter(RELEASE_PREFIX) }}
                // }
            )
    }
}
