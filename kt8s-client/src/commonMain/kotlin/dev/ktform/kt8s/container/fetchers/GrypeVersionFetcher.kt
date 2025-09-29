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
import dev.ktform.kt8s.container.components.GrypeComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.GrypeVersion

object GrypeVersionFetcher : VersionsFetcher<GrypeVersion> {
    override suspend fun getVersions(last: Int): Map<Component<GrypeVersion>, List<String>> =
        GrypeComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<GrypeVersion>): Option<String> =
        when (component) {
            is GrypeComponent if component == GrypeComponent.Grype ->
                "https://github.com/anchore/grype".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<GrypeVersion>): Option<String> =
        when (component) {
            is GrypeComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<GrypeVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is GrypeComponent -> listOf("0.100.0", "0.99.1", "0.99.0", "0.98.0", "0.97.2")
            else -> emptyList()
        }
}
