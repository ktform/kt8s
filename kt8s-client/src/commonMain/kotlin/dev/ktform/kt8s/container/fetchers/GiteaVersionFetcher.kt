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
import dev.ktform.kt8s.container.components.GiteaComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.GiteaVersion

object GiteaVersionFetcher : VersionsFetcher<GiteaVersion> {
    override suspend fun getVersions(last: Int): Map<Component<GiteaVersion>, List<String>> =
        GiteaComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<GiteaVersion>): Option<String> =
        when (component) {
            is GiteaComponent if component == GiteaComponent.Gitea ->
                "https://github.com/go-gitea/gitea".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<GiteaVersion>): Option<String> =
        when (component) {
            is GiteaComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<GiteaVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is GiteaComponent -> listOf("1.24.6", "1.24.5", "1.24.4", "1.24.3", "1.24.2")
            else -> emptyList()
        }
}
