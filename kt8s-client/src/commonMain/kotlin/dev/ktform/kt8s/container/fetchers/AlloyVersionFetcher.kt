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
import dev.ktform.kt8s.container.components.AlloyComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.AlloyVersion

object AlloyVersionFetcher : VersionsFetcher<AlloyVersion> {
    override suspend fun getVersions(last: Int): Map<Component<AlloyVersion>, List<String>> =
        AlloyComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<AlloyVersion>): Option<String> =
        when (component) {
            is AlloyComponent if component == AlloyComponent.Alloy ->
                "https://github.com/grafana/alloy".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<AlloyVersion>): Option<String> =
        when (component) {
            is AlloyComponent if component == AlloyComponent.Alloy -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<AlloyVersion>.knownLatestVersions(): List<String> =
        listOf("1.10.2", "1.10.1", "1.10.0", "1.9.2", "1.9.1")
}
