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
import dev.ktform.kt8s.container.components.KubeRayComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.KubeRayVersion

object KubeRayVersionFetcher : VersionsFetcher<KubeRayVersion> {
    override suspend fun getVersions(last: Int): Map<Component<KubeRayVersion>, List<String>> =
        KubeRayComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KubeRayVersion>): Option<String> =
        when (component) {
            is KubeRayComponent if component == KubeRayComponent.KubeRay ->
                "https://github.com/ray-project/kuberay".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KubeRayVersion>): Option<String> =
        when (component) {
            is KubeRayComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<KubeRayVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KubeRayComponent -> listOf("1.4.2", "1.4.1", "1.4.0", "1.3.2", "1.3.1")
            else -> emptyList()
        }
}
