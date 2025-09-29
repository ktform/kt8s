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
import dev.ktform.kt8s.container.components.MimirComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.MimirVersion

object MimirVersionFetcher : VersionsFetcher<MimirVersion> {
    override suspend fun getVersions(last: Int): Map<Component<MimirVersion>, List<String>> =
        MimirComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<MimirVersion>): Option<String> =
        when (component) {
            is MimirComponent if component == MimirComponent.Mimir ->
                "https://github.com/grafana/mimir".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<MimirVersion>): Option<String> =
        when (component) {
            is MimirComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<MimirVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is MimirComponent -> listOf("1.10.0", "1.9.0", "1.8.1", "1.8.0", "1.7.1")
            else -> emptyList()
        }
}
