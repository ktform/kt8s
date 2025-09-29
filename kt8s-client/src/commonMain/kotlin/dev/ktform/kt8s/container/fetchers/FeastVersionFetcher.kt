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
import dev.ktform.kt8s.container.components.FeastComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.FeastVersion

object FeastVersionFetcher : VersionsFetcher<FeastVersion> {
    override suspend fun getVersions(last: Int): Map<Component<FeastVersion>, List<String>> =
        FeastComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<FeastVersion>): Option<String> =
        when (component) {
            is FeastComponent if component == FeastComponent.Feast ->
                "https://github.com/feast-dev/feast".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<FeastVersion>): Option<String> =
        when (component) {
            is FeastComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<FeastVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is FeastComponent -> listOf("0.53.0", "0.52.0", "0.51.0", "0.50.0", "0.49.0")
            else -> emptyList()
        }
}
