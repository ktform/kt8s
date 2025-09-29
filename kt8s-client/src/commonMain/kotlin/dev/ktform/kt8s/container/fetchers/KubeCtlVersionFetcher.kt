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
import dev.ktform.kt8s.container.components.KubeCtlComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.KubeCtlVersion

object KubeCtlVersionFetcher : VersionsFetcher<KubeCtlVersion> {
    override suspend fun getVersions(last: Int): Map<Component<KubeCtlVersion>, List<String>> =
        KubeCtlComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KubeCtlVersion>): Option<String> =
        when (component) {
            is KubeCtlComponent if component == KubeCtlComponent.KubeCtl ->
                "https://github.com/kubernetes/kubectl".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KubeCtlVersion>): Option<String> =
        when (component) {
            is KubeCtlComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<KubeCtlVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KubeCtlComponent -> listOf("0.34.1", "0.34.0", "0.33.5", "0.33.4", "0.33.3")
            else -> emptyList()
        }
}
