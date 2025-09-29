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
import dev.ktform.kt8s.container.components.UVComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.UVVersion

object UVVersionFetcher : VersionsFetcher<UVVersion> {
    override suspend fun getVersions(last: Int): Map<Component<UVVersion>, List<String>> =
        UVComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<UVVersion>): Option<String> =
        when (component) {
            is UVComponent if component == UVComponent.UV ->
                "https://github.com/astral-sh/uv".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<UVVersion>): Option<String> =
        when (component) {
            is UVComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<UVVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is UVComponent -> listOf("0.8.22", "0.8.21", "0.8.20", "0.8.19", "0.8.18")
            else -> emptyList()
        }
}
