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
import dev.ktform.kt8s.container.components.DeschedulerComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.DeschedulerVersion

object DeschedulerVersionFetcher : VersionsFetcher<DeschedulerVersion> {
    override suspend fun getVersions(last: Int): Map<Component<DeschedulerVersion>, List<String>> =
        DeschedulerComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<DeschedulerVersion>): Option<String> =
        when (component) {
            is DeschedulerComponent if component == DeschedulerComponent.Descheduler ->
                "https://github.com/kubernetes-sigs/descheduler".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<DeschedulerVersion>): Option<String> =
        when (component) {
            is DeschedulerComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<DeschedulerVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is DeschedulerComponent -> listOf("0.33.0", "0.32.2")
            else -> emptyList()
        }
}
