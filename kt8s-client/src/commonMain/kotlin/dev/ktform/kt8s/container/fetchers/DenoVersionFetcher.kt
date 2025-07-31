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
import dev.ktform.kt8s.container.components.DenoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.DenoVersion

object DenoVersionFetcher : VersionsFetcher<DenoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<DenoVersion>, List<String>> =
        DenoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<DenoVersion>): Option<String> =
        when (component) {
            is DenoComponent if component == DenoComponent.Deno ->
                "https://github.com/denoland/deno".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<DenoVersion>): Option<String> =
        when (component) {
            is DenoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<DenoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is DenoComponent -> listOf("2.4.3", "2.4.2", "2.4.1")
            else -> emptyList()
        }
}
