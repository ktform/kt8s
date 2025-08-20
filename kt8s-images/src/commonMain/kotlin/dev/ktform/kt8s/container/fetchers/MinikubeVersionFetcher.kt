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
import dev.ktform.kt8s.container.components.MinikubeComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.MinikubeVersion

object MinikubeVersionFetcher : VersionsFetcher<MinikubeVersion> {
    override suspend fun getVersions(last: Int): Map<Component<MinikubeVersion>, List<String>> =
        MinikubeComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<MinikubeVersion>): Option<String> =
        when (component) {
            is MinikubeComponent if component == MinikubeComponent.Minikube ->
                "https://github.com/kubernetes/minikube".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<MinikubeVersion>): Option<String> =
        when (component) {
            is MinikubeComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<MinikubeVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is MinikubeComponent -> listOf("1.36.0", "1.35.0", "1.34.0")
            else -> emptyList()
        }
}
