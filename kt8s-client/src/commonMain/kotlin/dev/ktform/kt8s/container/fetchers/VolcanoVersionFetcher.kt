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
import dev.ktform.kt8s.container.components.VolcanoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.VolcanoVersion

object VolcanoVersionFetcher : VersionsFetcher<VolcanoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<VolcanoVersion>, List<String>> =
        VolcanoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<VolcanoVersion>): Option<String> =
        when (component) {
            is VolcanoComponent if component == VolcanoComponent.Volcano ->
                "https://github.com/volcano-sh/volcano".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<VolcanoVersion>): Option<String> =
        when (component) {
            is VolcanoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<VolcanoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is VolcanoComponent -> listOf("1.13.0", "1.12.2", "1.12.1", "1.12.0", "1.11.2")
            else -> emptyList()
        }
}
