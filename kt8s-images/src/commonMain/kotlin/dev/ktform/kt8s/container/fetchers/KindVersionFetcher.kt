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
import dev.ktform.kt8s.container.components.KindComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.KindVersion

object KindVersionFetcher : VersionsFetcher<KindVersion> {
    override suspend fun getVersions(last: Int): Map<Component<KindVersion>, List<String>> =
        KindComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KindVersion>): Option<String> =
        when (component) {
            is KindComponent if component == KindComponent.Kind ->
                "https://github.com/kubernetes-sigs/kind".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KindVersion>): Option<String> =
        when (component) {
            is KindComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<KindVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KindComponent -> listOf("0.29.0", "0.28.0")
            else -> emptyList()
        }
}
