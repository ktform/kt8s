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
import dev.ktform.kt8s.container.components.CorazaComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.CorazaVersion

object CorazaVersionFetcher : VersionsFetcher<CorazaVersion> {
    override suspend fun getVersions(last: Int): Map<Component<CorazaVersion>, List<String>> =
        CorazaComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<CorazaVersion>): Option<String> =
        when (component) {
            is CorazaComponent if component == CorazaComponent.Coraza ->
                "https://github.com/corazawaf/coraza".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<CorazaVersion>): Option<String> =
        when (component) {
            is CorazaComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<CorazaVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is CorazaComponent -> listOf("3.3.3", "3.3.2", "3.3.1")

            else -> emptyList()
        }
}
