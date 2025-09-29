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
import dev.ktform.kt8s.container.components.Kt8sComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.Kt8sVersion

object Kt8sVersionFetcher : VersionsFetcher<Kt8sVersion> {
    override suspend fun getVersions(last: Int): Map<Component<Kt8sVersion>, List<String>> =
        Kt8sComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<Kt8sVersion>): Option<String> =
        when (component) {
            is KindComponent if component == KindComponent.Kind ->
                "https://github.com/ktform/kt8s".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<Kt8sVersion>): Option<String> =
        when (component) {
            is KindComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<Kt8sVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KindComponent -> listOf("0.1.0")
            else -> emptyList()
        }
}
