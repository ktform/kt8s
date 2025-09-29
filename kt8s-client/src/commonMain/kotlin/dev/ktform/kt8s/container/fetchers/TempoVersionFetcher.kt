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
import dev.ktform.kt8s.container.components.TempoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.TempoVersion

object TempoVersionFetcher : VersionsFetcher<TempoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<TempoVersion>, List<String>> =
        TempoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<TempoVersion>): Option<String> =
        when (component) {
            is TempoComponent if component == TempoComponent.Tempo ->
                "https://github.com/grafana/tempo".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<TempoVersion>): Option<String> =
        when (component) {
            is TempoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<TempoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is TempoComponent -> listOf("2.8.2", "2.8.1", "2.8.0", "2.7.2", "2.7.1")
            else -> emptyList()
        }
}
