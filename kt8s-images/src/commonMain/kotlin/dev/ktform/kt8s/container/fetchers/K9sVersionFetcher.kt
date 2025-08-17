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
import dev.ktform.kt8s.container.components.K9sComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.K9sVersion

object K9sVersionFetcher : VersionsFetcher<K9sVersion> {
    override suspend fun getVersions(last: Int): Map<Component<K9sVersion>, List<String>> =
        K9sComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<K9sVersion>): Option<String> =
        when (component) {
            is K9sComponent if component == K9sComponent.K9s ->
                "https://github.com/derailed/k9s".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<K9sVersion>): Option<String> =
        when (component) {
            is K9sComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<K9sVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is K9sComponent -> listOf("0.50.9", "0.50.8")
            else -> emptyList()
        }
}
