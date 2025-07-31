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
import dev.ktform.kt8s.container.components.KubeFlinkComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.versions.KubeFlinkVersion

object KubeFlinkVersionFetcher : VersionsFetcher<KubeFlinkVersion> {

    const val RELEASE_PREFIX = "release-"

    override suspend fun getVersions(last: Int): Map<Component<KubeFlinkVersion>, List<String>> =
        KubeFlinkComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, prefix = RELEASE_PREFIX).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KubeFlinkVersion>): Option<String> =
        when (component) {
            is KubeFlinkComponent if component == KubeFlinkComponent.KubeFlink ->
                "https://github.com/apache/flink-kubernetes-operator".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KubeFlinkVersion>): Option<String> =
        when (component) {
            is KubeFlinkComponent -> "$RELEASE_PREFIX$this".some()
            else -> None
        }

    override fun Component<KubeFlinkVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KubeFlinkComponent -> listOf("1.12.1", "1.12.0", "1.11.0")
            else -> emptyList()
        }
}
