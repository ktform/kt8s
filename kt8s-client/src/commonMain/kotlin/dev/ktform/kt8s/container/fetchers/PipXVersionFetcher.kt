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
import dev.ktform.kt8s.container.components.PipXComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.PipXVersion

object PipXVersionFetcher : VersionsFetcher<PipXVersion> {
    override suspend fun getVersions(last: Int): Map<Component<PipXVersion>, List<String>> =
        PipXComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<PipXVersion>): Option<String> =
        when (component) {
            is PipXComponent if component == PipXComponent.PipX ->
                "https://github.com/pypa/pipx".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<PipXVersion>): Option<String> =
        when (component) {
            is PipXComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<PipXVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is PipXComponent -> listOf("1.7.1", "1.7.0", "1.6.0", "1.5.0", "1.4.3")
            else -> emptyList()
        }
}
