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
package dev.ktform.kt8s.container.fetchers

import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.DoCtlComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.DoCtlVersion

object DoCtlVersionFetcher : VersionsFetcher<DoCtlVersion> {
    override suspend fun getVersions(last: Int): Map<Component<DoCtlVersion>, List<String>> =
        DoCtlComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<DoCtlVersion>): Option<String> =
        when (component) {
            is DoCtlComponent if component == DoCtlComponent.DoCtl ->
                "https://github.com/digitalocean/doctl".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<DoCtlVersion>): Option<String> =
        when (component) {
            is DoCtlComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<DoCtlVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is DoCtlComponent -> listOf("1.145.0", "1.143.0", "1.142.0", "1.141.0", "1.140.0")
            else -> emptyList()
        }
}
