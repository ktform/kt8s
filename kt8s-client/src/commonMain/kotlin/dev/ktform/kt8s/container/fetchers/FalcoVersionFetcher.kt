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
import dev.ktform.kt8s.container.components.FalcoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.FalcoVersion

object FalcoVersionFetcher : VersionsFetcher<FalcoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<FalcoVersion>, List<String>> =
        FalcoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<FalcoVersion>): Option<String> =
        when (component) {
            is FalcoComponent if component == FalcoComponent.Falco ->
                "https://github.com/falcosecurity/falco".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<FalcoVersion>): Option<String> =
        when (component) {
            is FalcoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<FalcoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is FalcoComponent -> listOf("0.41.3", "0.41.2", "0.41.1", "0.41.0", "0.40.0")
            else -> emptyList()
        }
}
