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
import dev.ktform.kt8s.container.components.CosignComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.CosignVersion

object CosignVersionFetcher : VersionsFetcher<CosignVersion> {
    override suspend fun getVersions(last: Int): Map<Component<CosignVersion>, List<String>> =
        CosignComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<CosignVersion>): Option<String> =
        when (component) {
            is CosignComponent if component == CosignComponent.Cosign ->
                "https://github.com/sigstore/cosign".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<CosignVersion>): Option<String> =
        when (component) {
            is CosignComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<CosignVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is CosignComponent -> listOf("2.6.0", "2.5.3", "2.5.2", "2.5.1", "2.5.0")
            else -> emptyList()
        }
}
