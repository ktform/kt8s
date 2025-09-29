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
import dev.ktform.kt8s.container.components.LokiComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.LokiVersion

object LokiVersionFetcher : VersionsFetcher<LokiVersion> {
    override suspend fun getVersions(last: Int): Map<Component<LokiVersion>, List<String>> =
        LokiComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<LokiVersion>): Option<String> =
        when (component) {
            is LokiComponent if component == LokiComponent.Loki ->
                "https://github.com/grafana/loki".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<LokiVersion>): Option<String> =
        when (component) {
            is LokiComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<LokiVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is LokiComponent -> listOf("3.5.5", "3.5.4", "3.5.3", "3.5.2", "3.5.1")
            else -> emptyList()
        }
}
