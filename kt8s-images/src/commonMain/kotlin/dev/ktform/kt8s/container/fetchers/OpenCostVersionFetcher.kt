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
import dev.ktform.kt8s.container.components.OpenCostComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.OpenCostVersion

object OpenCostVersionFetcher : VersionsFetcher<OpenCostVersion> {
    override suspend fun getVersions(last: Int): Map<Component<OpenCostVersion>, List<String>> =
        OpenCostComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<OpenCostVersion>): Option<String> =
        when (component) {
            is OpenCostComponent if component == OpenCostComponent.OpenCost ->
                "https://github.com/opencost/opencost".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<OpenCostVersion>): Option<String> =
        when (component) {
            is OpenCostComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<OpenCostVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is OpenCostComponent -> listOf("2.5.3", "2.5.2")
            else -> emptyList()
        }
}
