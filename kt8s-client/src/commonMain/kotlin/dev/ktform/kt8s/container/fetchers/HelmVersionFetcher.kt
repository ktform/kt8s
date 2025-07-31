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
import dev.ktform.kt8s.container.components.HelmComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.HelmVersion

object HelmVersionFetcher : VersionsFetcher<HelmVersion> {
    override suspend fun getVersions(last: Int): Map<Component<HelmVersion>, List<String>> =
        HelmComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<HelmVersion>): Option<String> =
        when (component) {
            is HelmComponent if component == HelmComponent.Helm ->
                "https://github.com/helm/helm".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<HelmVersion>): Option<String> =
        when (component) {
            is HelmComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<HelmVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is HelmComponent -> listOf("3.18.4", "3.18.3")
            else -> emptyList()
        }
}
