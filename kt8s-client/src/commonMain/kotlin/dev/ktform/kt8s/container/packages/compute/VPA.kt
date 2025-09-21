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

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.VpaVersionFetcher
import dev.ktform.kt8s.container.versions.VpaVersion
import dev.ktform.kt8s.container.versions.VpaVersion.Companion.toVpaVersion

class VPA(val versions: VpaVersion) : Renderable {
    constructor(version: String) : this(version.toVpaVersion())

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, VpaVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/kubernetes/autoscaler"

        val DEFAULT_VERSIONS = listOf("1.0.0", "0.14.0", "0.13.0")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "vpa"
                //      repo = REPO,
                //      availableVersions = { _ ->
                //        val client = GithubClient()
                //        client.getTags(REPO)
                //          .getOrElse { fallback }
                //          .mapNotNull { tag ->
                //            if (tag.startsWith("vertical-pod-autoscaler-"))
                // tag.removePrefix("vertical-pod-autoscaler-") else null
                //          }
                //          .filter { !it.contains("-") && !it.contains("rc") }
                //          .distinct()
                //      },
                //      repoVersion = { v, toRepo -> if (toRepo) "vertical-pod-autoscaler-$v" else v
                // },
            )
    }
}
