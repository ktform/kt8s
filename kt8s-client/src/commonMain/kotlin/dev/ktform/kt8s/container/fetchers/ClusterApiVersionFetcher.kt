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
import dev.ktform.kt8s.container.components.ClusterApiComponent
import dev.ktform.kt8s.container.components.CmakeComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.ClusterApiVersion

object ClusterApiVersionFetcher : VersionsFetcher<ClusterApiVersion> {
    override suspend fun getVersions(last: Int): Map<Component<ClusterApiVersion>, List<String>> =
        ClusterApiComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ClusterApiVersion>): Option<String> =
        when (component) {
            is CmakeComponent -> "https://github.com/kubernetes-sigs/cluster-api".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ClusterApiVersion>): Option<String> =
        when (component) {
            is CmakeComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ClusterApiVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is CmakeComponent -> listOf("1.11.1")
            else -> emptyList()
        }
}
